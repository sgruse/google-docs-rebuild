CREATE TABLE document_snapshots (
  snapshot_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  document_id UUID NOT NULL REFERENCES documents(document_id),
  crdt_state BYTEA,
  content_text TEXT,
  vector_clock JSONB NOT NULL DEFAULT '{}'::jsonb,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT uq_snapshot_document UNIQUE (document_id)
);

CREATE INDEX idx_snapshots_document_id ON document_snapshots(document_id);
