import { Routes, Route, Navigate } from 'react-router-dom'
import DocumentPage from './pages/DocumentPage'

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/doc/demo-doc" replace />} />
      <Route path="/doc/:documentId" element={<DocumentPage />} />
    </Routes>
  )
}
