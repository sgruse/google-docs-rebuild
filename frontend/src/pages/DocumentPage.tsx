import { useParams, Navigate } from 'react-router-dom'
import Editor from '../components/Editor'

export default function DocumentPage() {
  const { documentId } = useParams()

  if (!documentId) {
    return <Navigate to="/doc/demo-doc" replace />
  }

  return (
    <div className="document-page">
      <Editor key={documentId} documentId={documentId} />
    </div>
  )
}
