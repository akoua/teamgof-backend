ALTER TABLE IF EXISTS cavalier
    ADD COLUMN IF NOT EXISTS email  VARCHAR(255) NOT NULL,
    ADD COLUMN IF NOT EXISTS pwd  VARCHAR(1000) NOT NULL;
