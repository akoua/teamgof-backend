ALTER TABLE IF EXISTS cavalier_team_participated
    ADD CONSTRAINT uc_cavalier_team UNIQUE (cavalier_id, team_id);
