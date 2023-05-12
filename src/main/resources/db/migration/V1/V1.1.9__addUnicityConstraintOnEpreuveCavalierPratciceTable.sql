ALTER TABLE IF EXISTS cavalier_epreuve_practice
    ADD CONSTRAINT uc_cavalier_epreuve UNIQUE (cavalier_id, epreuve_id);
