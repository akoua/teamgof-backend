ALTER TABLE IF EXISTS cavalier
    ADD COLUMN IF NOT EXISTS role VARCHAR(255);

UPDATE cavalier
SET role = 'CAVALIER';

ALTER TABLE IF EXISTS cavalier
    ALTER COLUMN role SET NOT NULL;