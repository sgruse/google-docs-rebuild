package com.doccollab.backend.controller;

import com.doccollab.backend.dto.DocumentResponse;
import com.doccollab.backend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentResponse> createDocument(@RequestBody Map<String, String> body) {
        String sessionId = body.getOrDefault("sessionId", "anonymous");
        return ResponseEntity.ok(documentService.createDocument(sessionId));
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable UUID documentId) {
        return ResponseEntity.ok(documentService.getDocument(documentId));
    }
}
