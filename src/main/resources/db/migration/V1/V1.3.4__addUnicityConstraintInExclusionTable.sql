ALTER TABLE IF EXISTS exclusion
    ADD CONSTRAINT exclusion_label_uc UNIQUE (label),
    ALTER COLUMN label SET NOT NULL;