CREATE TABLE writes (
  write_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  document_id UUID NOT NULL REFERENCES documents(document_id),
  session_id TEXT NOT NULL,
  user_sequence_number INT NOT NULL,
  operations JSONB NOT NULL,
  vector_clock JSONB NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_writes_document_id ON writes(document_id);
CREATE INDEX idx_writes_session_sequence ON writes(document_id, session_id, user_sequence_number);
CREATE INDEX idx_writes_created_at ON writes(document_id, created_at);
