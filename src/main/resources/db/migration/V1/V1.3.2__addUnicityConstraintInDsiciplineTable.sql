ALTER TABLE IF EXISTS discipline
    ADD CONSTRAINT discipline_name_uc UNIQUE (name),
    ALTER COLUMN name SET NOT NULL;