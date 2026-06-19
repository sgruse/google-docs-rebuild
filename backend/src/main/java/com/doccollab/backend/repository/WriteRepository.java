package com.doccollab.backend.repository;

import com.doccollab.backend.model.Write;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WriteRepository extends JpaRepository<Write, UUID> {

    List<Write> findByDocumentIdOrderByCreatedAtAsc(UUID documentId);

    List<Write> findByDocumentIdAndSessionIdAndUserSequenceNumberGreaterThan(
            UUID documentId, String sessionId, Integer sequenceNumber);
}
