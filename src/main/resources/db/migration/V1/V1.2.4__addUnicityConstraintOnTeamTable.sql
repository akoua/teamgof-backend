ALTER TABLE IF EXISTS team
    ADD CONSTRAINT uc_team_name UNIQUE (name);
