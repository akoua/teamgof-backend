ALTER TABLE IF EXISTS cavalier_epreuve_participated
    DROP CONSTRAINT IF EXISTS FK_CAVALIER_EPREUVE_PARTICIPATED_ON_CAVALIER;
ALTER TABLE IF EXISTS cavalier_epreuve_participated
    DROP CONSTRAINT IF EXISTS FK_CAVALIER_EPREUVE_PARTICIPATED_ON_EPREUVE;

DROP TABLE IF EXISTS cavalier_epreuve_participated;