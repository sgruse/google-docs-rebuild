package com.doccollab.backend.service;

import com.doccollab.backend.dto.DocumentResponse;
import com.doccollab.backend.model.Document;
import com.doccollab.backend.model.DocumentSnapshot;
import com.doccollab.backend.repository.DocumentRepository;
import com.doccollab.backend.repository.DocumentSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentSnapshotRepository snapshotRepository;

    public DocumentResponse createDocument(String sessionId) {
        Document doc = new Document();
        doc.setOwnerSessionId(sessionId);
        doc.setTitle("Untitled Document");
        Document saved = documentRepository.save(doc);
        return toResponse(saved, null);
    }

    public DocumentResponse getDocument(UUID documentId) {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found: " + documentId));
        DocumentSnapshot snapshot = snapshotRepository.findByDocumentId(documentId).orElse(null);
        return toResponse(doc, snapshot);
    }

    public boolean documentExists(UUID documentId) {
        return documentRepository.existsById(documentId);
    }

    private DocumentResponse toResponse(Document doc, DocumentSnapshot snapshot) {
        DocumentResponse response = new DocumentResponse();
        response.setDocumentId(doc.getDocumentId().toString());
        response.setTitle(doc.getTitle());
        response.setCreatedAt(doc.getCreatedAt() != null ? doc.getCreatedAt().toString() : null);
        if (snapshot != null) {
            response.setCrdtState(snapshot.getCrdtState());
            response.setVectorClock(snapshot.getVectorClock());
        } else {
            response.setVectorClock("{}");
        }
        return response;
    }
}
