ALTER TABLE IF EXISTS cavalier_epreuve_practice
    RENAME COLUMN qualification TO qualification_cavalier;

ALTER TABLE IF EXISTS cavalier_epreuve_practice
    ALTER COLUMN qualification_cavalier SET DATA TYPE INTEGER
        USING CAST(qualification_cavalier AS integer);

ALTER TABLE IF EXISTS cavalier_epreuve_practice
    ALTER COLUMN qualification_cavalier SET NOT NULL;

