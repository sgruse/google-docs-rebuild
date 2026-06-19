package com.doccollab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriteResponse {
    private String writeId;
    private String documentId;
    private String sessionId;
    private Integer userSequenceNumber;
    private String vectorClock;
    private String createdAt;
}
