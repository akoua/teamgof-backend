ALTER TABLE IF EXISTS cavalier
    ADD CONSTRAINT uc_cavalier_email UNIQUE (email);