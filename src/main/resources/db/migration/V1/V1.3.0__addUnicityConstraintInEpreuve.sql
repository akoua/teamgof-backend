ALTER TABLE IF EXISTS epreuve
    ADD CONSTRAINT epreuve_discipline_name_uc UNIQUE (name, discipline_id);