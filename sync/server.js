import 'dotenv/config' // loads env vars automatically and makes them available at process.env
import http from 'http'
import { WebSocketServer } from 'ws'
import { createRequire } from 'module'

/**
 * Yjs only exists as JS library. To make it work with the Java backend, we run a small Node microservice to handle moving
 * CRDT updates between clients as fast as possible. Java still owns the stateful logic.
 */

// y-websocket 1.x ships CommonJS utilities; bridge them into this ESM module
const require = createRequire(import.meta.url)

/**
 * Core of the sync server. This is Yjs's built in handler that manages CRDT sync protocol.
 * When a client connects, this function handles receiving updates, broadcasting changes, and managing document state in memory.
 */
const { setupWSConnection } = require('y-websocket/bin/utils')

const PORT = process.env.PORT ?? 1234

const server = http.createServer((req, res) => {
  res.writeHead(200, { 'Content-Type': 'text/plain' })
  res.end('Sync server')
})

// Creates the websocket server on top of the HTTP server.
const wss = new WebSocketServer({ server })

// Each connecting client's URL path becomes the document room ID, e.g. /doc-abc-123
wss.on('connection', (ws, req) => setupWSConnection(ws, req))

server.listen(PORT, () => {
  console.log(`Sync server running on port ${PORT}`)
})
