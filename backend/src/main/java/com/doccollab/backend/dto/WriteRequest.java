package com.doccollab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriteRequest {
    private String documentId;
    private String sessionId;
    private Integer userSequenceNumber;
    private String operations;
    private String vectorClock;
}
