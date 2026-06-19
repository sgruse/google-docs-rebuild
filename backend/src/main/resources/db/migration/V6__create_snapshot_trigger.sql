CREATE OR REPLACE FUNCTION check_snapshot_threshold()
RETURNS TRIGGER AS $$
DECLARE
  write_threshold INT := 50;
  time_threshold INTERVAL := '5 minutes';
BEGIN
  INSERT INTO snapshot_jobs (document_id, pending_writes, last_snapshot_at)
  VALUES (NEW.document_id, 1, NOW())
  ON CONFLICT (document_id) DO UPDATE
    SET pending_writes = snapshot_jobs.pending_writes + 1,
        snapshot_due = (
          snapshot_jobs.pending_writes + 1 >= write_threshold
          OR NOW() - snapshot_jobs.last_snapshot_at >= time_threshold
        );
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_write_insert
AFTER INSERT ON writes
FOR EACH ROW EXECUTE FUNCTION check_snapshot_threshold();
