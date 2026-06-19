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
@Table(name = "document_snapshots")
public class DocumentSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "snapshot_id")
    private UUID snapshotId;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Column(name = "crdt_state")
    private byte[] crdtState;

    @Column(name = "content_text")
    private String contentText;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "vector_clock", columnDefinition = "jsonb")
    private String vectorClock;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (vectorClock == null) vectorClock = "{}";
    }
}
