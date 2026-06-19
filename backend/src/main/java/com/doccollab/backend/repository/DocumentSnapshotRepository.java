package com.doccollab.backend.repository;

import com.doccollab.backend.model.DocumentSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentSnapshotRepository extends JpaRepository<DocumentSnapshot, UUID> {

    Optional<DocumentSnapshot> findByDocumentId(UUID documentId);
}
