CREATE TABLE dead_letter_writes (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  document_id UUID,
  session_id TEXT,
  payload JSONB,
  error_message TEXT,
  failed_at TIMESTAMP NOT NULL DEFAULT NOW(),
  retry_count INT NOT NULL DEFAULT 0
);
