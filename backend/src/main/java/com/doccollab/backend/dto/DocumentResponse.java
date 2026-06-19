package com.doccollab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    private String documentId;
    private String title;
    private byte[] crdtState;
    private String vectorClock;
    private String createdAt;
}
