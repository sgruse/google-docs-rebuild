package com.doccollab.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "writes")
public class Write {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "write_id")
    private UUID writeId;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "user_sequence_number", nullable = false)
    private Integer userSequenceNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "operations", columnDefinition = "jsonb", nullable = false)
    private String operations;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "vector_clock", columnDefinition = "jsonb", nullable = false)
    private String vectorClock;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
