CREATE SEQUENCE IF NOT EXISTS cavalier_id_seq START WITH 8 INCREMENT BY 1;;

ALTER TABLE IF EXISTS cavalier
    ALTER COLUMN cavalier_id SET DATA TYPE BIGINT,
    ALTER COLUMN cavalier_id SET DEFAULT nextval('cavalier_id_seq'),
    ALTER COLUMN cavalier_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS cavalier_id_seq
    OWNED BY cavalier.cavalier_id;

-------

CREATE SEQUENCE IF NOT EXISTS poney_id_seq START WITH 2 INCREMENT BY 1;;

ALTER TABLE IF EXISTS poney
    ALTER COLUMN poney_id SET DATA TYPE BIGINT,
    ALTER COLUMN poney_id SET DEFAULT nextval('poney_id_seq'),
    ALTER COLUMN poney_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS poney_id_seq
    OWNED BY poney.poney_id;

-------

CREATE SEQUENCE IF NOT EXISTS discipline_id_seq START WITH 3 INCREMENT BY 1;;

ALTER TABLE IF EXISTS discipline
    ALTER COLUMN discipline_id SET DATA TYPE BIGINT,
    ALTER COLUMN discipline_id SET DEFAULT nextval('discipline_id_seq'),
    ALTER COLUMN discipline_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS discipline_id_seq
    OWNED BY discipline.discipline_id;

-------

CREATE SEQUENCE IF NOT EXISTS cavalier_epreuve_practice_id_seq START WITH 10 INCREMENT BY 1;;

ALTER TABLE IF EXISTS cavalier_epreuve_practice
    ALTER COLUMN cavalier_epreuve_practice_id SET DATA TYPE BIGINT,
    ALTER COLUMN cavalier_epreuve_practice_id SET DEFAULT nextval('cavalier_epreuve_practice_id_seq'),
    ALTER COLUMN cavalier_epreuve_practice_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS cavalier_epreuve_practice_id_seq
    OWNED BY cavalier_epreuve_practice.cavalier_epreuve_practice_id;

-------

CREATE SEQUENCE IF NOT EXISTS epreuve_id_seq START WITH 5 INCREMENT BY 1;;

ALTER TABLE IF EXISTS epreuve
    ALTER COLUMN epreuve_id SET DATA TYPE BIGINT,
    ALTER COLUMN epreuve_id SET DEFAULT nextval('epreuve_id_seq'),
    ALTER COLUMN epreuve_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS epreuve_id_seq
    OWNED BY epreuve.epreuve_id;

-------

CREATE SEQUENCE IF NOT EXISTS cavalier_team_participated_id_seq START WITH 1 INCREMENT BY 1;;

ALTER TABLE IF EXISTS cavalier_team_participated
    ALTER COLUMN cavalier_team_participated_id SET DATA TYPE BIGINT,
    ALTER COLUMN cavalier_team_participated_id SET DEFAULT nextval('cavalier_team_participated_id_seq'),
    ALTER COLUMN cavalier_team_participated_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS cavalier_team_participated_id_seq
    OWNED BY cavalier_team_participated.cavalier_team_participated_id;

-------

CREATE SEQUENCE IF NOT EXISTS epreuve_team_participated_id_seq START WITH 1 INCREMENT BY 1;;

ALTER TABLE IF EXISTS epreuve_team_participated
    ALTER COLUMN epreuve_team_participated_id SET DATA TYPE BIGINT,
    ALTER COLUMN epreuve_team_participated_id SET DEFAULT nextval('epreuve_team_participated_id_seq'),
    ALTER COLUMN epreuve_team_participated_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS epreuve_team_participated_id_seq
    OWNED BY epreuve_team_participated.epreuve_team_participated_id;

-------

CREATE SEQUENCE IF NOT EXISTS team_id_seq START WITH 9 INCREMENT BY 1;;

ALTER TABLE IF EXISTS team
    ALTER COLUMN team_id SET DATA TYPE BIGINT,
    ALTER COLUMN team_id SET DEFAULT nextval('team_id_seq'),
    ALTER COLUMN team_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS team_id_seq
    OWNED BY team.team_id;

-------

CREATE SEQUENCE IF NOT EXISTS refresh_token_id_seq START WITH 9 INCREMENT BY 1;;

ALTER TABLE IF EXISTS refresh_token
    ALTER COLUMN refresh_token_id SET DATA TYPE BIGINT,
    ALTER COLUMN refresh_token_id SET DEFAULT nextval('refresh_token_id_seq'),
    ALTER COLUMN refresh_token_id SET NOT NULL;

ALTER SEQUENCE IF EXISTS refresh_token_id_seq
    OWNED BY refresh_token.refresh_token_id;

-------

-- CREATE SEQUENCE IF NOT EXISTS cavalier_epreuve_participated_id_seq START WITH 1 INCREMENT BY 1;;
--
-- ALTER TABLE IF EXISTS cavalier_epreuve_participated
--     ALTER COLUMN cavalier_epreuve_participated_id SET DATA TYPE BIGINT,
--     ALTER COLUMN cavalier_epreuve_participated_id SET DEFAULT nextval('cavalier_epreuve_participated_id_seq'),
--     ALTER COLUMN cavalier_epreuve_participated_id SET NOT NULL;
--
-- ALTER SEQUENCE IF EXISTS cavalier_epreuve_participated_id_seq
--     OWNED BY cavalier_epreuve_participated.cavalier_epreuve_participated_id;