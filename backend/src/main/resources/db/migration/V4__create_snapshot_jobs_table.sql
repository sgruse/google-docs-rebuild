CREATE TABLE snapshot_jobs (
  document_id UUID PRIMARY KEY REFERENCES documents(document_id),
  pending_writes INT NOT NULL DEFAULT 0,
  last_snapshot_at TIMESTAMP NOT NULL DEFAULT NOW(),
  snapshot_due BOOLEAN NOT NULL DEFAULT FALSE
);
