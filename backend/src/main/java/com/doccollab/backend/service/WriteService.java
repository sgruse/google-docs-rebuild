package com.doccollab.backend.service;

import com.doccollab.backend.dto.WriteRequest;
import com.doccollab.backend.dto.WriteResponse;
import com.doccollab.backend.model.Write;
import com.doccollab.backend.repository.WriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WriteService {

    private final WriteRepository writeRepository;
    private final DocumentService documentService;

    public WriteResponse persistWrite(WriteRequest request) {
        UUID documentId = UUID.fromString(request.getDocumentId());
        if (!documentService.documentExists(documentId)) {
            documentService.createDocument(request.getSessionId());
        }
        Write write = new Write();
        write.setDocumentId(documentId);
        write.setSessionId(request.getSessionId());
        write.setUserSequenceNumber(request.getUserSequenceNumber());
        write.setOperations(request.getOperations());
        write.setVectorClock(request.getVectorClock());
        Write saved = writeRepository.save(write);
        return toResponse(saved);
    }

    public List<WriteResponse> getWritesSince(UUID documentId, String sessionId, Integer sequenceNumber) {
        return writeRepository
                .findByDocumentIdAndSessionIdAndUserSequenceNumberGreaterThan(documentId, sessionId, sequenceNumber)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private WriteResponse toResponse(Write write) {
        WriteResponse response = new WriteResponse();
        response.setWriteId(write.getWriteId().toString());
        response.setDocumentId(write.getDocumentId().toString());
        response.setSessionId(write.getSessionId());
        response.setUserSequenceNumber(write.getUserSequenceNumber());
        response.setVectorClock(write.getVectorClock());
        response.setCreatedAt(write.getCreatedAt() != null ? write.getCreatedAt().toString() : null);
        return response;
    }
}
