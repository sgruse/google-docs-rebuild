import { useState, useEffect, useMemo } from 'react'
import * as Y from 'yjs'
import { WebsocketProvider } from 'y-websocket'
import { useEditor, EditorContent } from '@tiptap/react'
import StarterKit from '@tiptap/starter-kit'
import Collaboration from '@tiptap/extension-collaboration'
import CollaborationCursor from '@tiptap/extension-collaboration-cursor'

const COLORS = ['#f783ac', '#74c0fc', '#69db7c', '#ffd43b', '#ff8787', '#a9e34b', '#cc5de8']

type ConnectionStatus = 'connecting' | 'connected' | 'disconnected'

interface EditorProps {
  documentId: string
}

export default function Editor({ documentId }: EditorProps) {
  const [status, setStatus] = useState<ConnectionStatus>('connecting')

  const [userColor] = useState(() => COLORS[Math.floor(Math.random() * COLORS.length)])
  const [userName] = useState(() => `User ${Math.floor(Math.random() * 9000) + 1000}`)

  const ydoc = useMemo(() => new Y.Doc(), [])

  const provider = useMemo(() => {
    const wsUrl = import.meta.env.VITE_SYNC_URL ?? 'ws://localhost:1234'
    return new WebsocketProvider(wsUrl, documentId, ydoc)
  }, [documentId, ydoc])

  const editor = useEditor({
    extensions: [
      StarterKit.configure({ history: false }),
      Collaboration.configure({ document: ydoc }),
      CollaborationCursor.configure({
        provider,
        user: { name: userName, color: userColor },
      }),
    ],
  })

  useEffect(() => {
    const handler = ({ status }: { status: string }) => {
      setStatus(status as ConnectionStatus)
    }
    provider.on('status', handler)
    return () => provider.off('status', handler)
  }, [provider])

  useEffect(() => {
    return () => {
      provider.destroy()
      ydoc.destroy()
    }
  }, [provider, ydoc])

  const statusLabel: Record<ConnectionStatus, string> = {
    connecting: 'Connecting...',
    connected: 'Connected',
    disconnected: 'Disconnected',
  }

  return (
    <div className="editor-container">
      <div className="toolbar">
        <span className="doc-id">{documentId}</span>
        <span className={`status status--${status}`}>{statusLabel[status]}</span>
      </div>
      <EditorContent editor={editor} className="editor-content" />
    </div>
  )
}
