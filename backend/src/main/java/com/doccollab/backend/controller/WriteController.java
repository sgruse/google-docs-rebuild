package com.doccollab.backend.controller;

import com.doccollab.backend.dto.WriteRequest;
import com.doccollab.backend.dto.WriteResponse;
import com.doccollab.backend.service.WriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/writes")
@RequiredArgsConstructor
public class WriteController {

    private final WriteService writeService;

    @PostMapping
    public ResponseEntity<WriteResponse> persistWrite(@RequestBody WriteRequest request) {
        return ResponseEntity.ok(writeService.persistWrite(request));
    }

    @GetMapping("/{documentId}/since")
    public ResponseEntity<List<WriteResponse>> getWritesSince(
            @PathVariable UUID documentId,
            @RequestParam String sessionId,
            @RequestParam Integer sequenceNumber) {
        return ResponseEntity.ok(writeService.getWritesSince(documentId, sessionId, sequenceNumber));
    }
}
