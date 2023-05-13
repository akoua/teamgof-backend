ALTER TABLE IF EXISTS epreuve_team_participated
    ADD CONSTRAINT uc_epreuve_team UNIQUE (epreuve_id, team_id);
