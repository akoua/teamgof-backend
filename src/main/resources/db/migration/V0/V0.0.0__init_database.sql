CREATE SCHEMA gof_team;

SET search_path TO gof_team;

CREATE TABLE cavalier
(
    cavalier_id        BIGINT                                    NOT NULL,
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    birth_date         TIMESTAMP WITHOUT TIME ZONE,
    location           VARCHAR(255),
    niveau             VARCHAR(255),
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by   VARCHAR(255),
    CONSTRAINT pk_cavalier PRIMARY KEY (cavalier_id)
);

CREATE TABLE discipline
(
    discipline_id      BIGINT                                    NOT NULL,
    name               VARCHAR(255),
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by   VARCHAR(255),
    CONSTRAINT pk_discipline PRIMARY KEY (discipline_id)
);

CREATE TABLE poney
(
    poney_id           BIGINT                                    NOT NULL,
    cavalier_id        BIGINT,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by   VARCHAR(255),
    CONSTRAINT pk_poney PRIMARY KEY (poney_id)
);

ALTER TABLE poney
    ADD CONSTRAINT FK_PONEY_ON_CAVALIER FOREIGN KEY (cavalier_id) REFERENCES cavalier (cavalier_id);

CREATE TABLE cavalier_team_participated
(
    cavalier_team_participated_id BIGINT                                    NOT NULL,
    created_by                    VARCHAR(255),
    created_date                  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date            TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by              VARCHAR(255),
    cavalier_id                   BIGINT,
    team_id                       BIGINT,
    CONSTRAINT pk_cavalier_team_participated PRIMARY KEY (cavalier_team_participated_id)
);

CREATE TABLE epreuve_team_participated
(
    epreuve_team_participated_id BIGINT                                    NOT NULL,
    created_by                   VARCHAR(255),
    created_date                 TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by             VARCHAR(255),
    epreuve_id                   BIGINT,
    team_id                      BIGINT,
    CONSTRAINT pk_epreuve_team_participated PRIMARY KEY (epreuve_team_participated_id)
);

CREATE TABLE cavalier_epreuve_participated
(
    cavalier_epreuve_participated_id BIGINT                                    NOT NULL,
    created_by                       VARCHAR(255),
    created_date                     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date               TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by                 VARCHAR(255),
    cavalier_id                      BIGINT,
    epreuve_id                       BIGINT,
    CONSTRAINT pk_cavalier_epreuve_participated PRIMARY KEY (cavalier_epreuve_participated_id)
);

CREATE TABLE cavalier_epreuve_practice
(
    cavalier_epreuve_practice_id BIGINT                                    NOT NULL,
    created_by                   VARCHAR(255),
    created_date                 TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date           TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by             VARCHAR(255),
    cavalier_id                  BIGINT,
    epreuve_id                   BIGINT,
    cavalier_qualification       DOUBLE PRECISION,
    "equidé_qualification"       DOUBLE PRECISION,
    CONSTRAINT pk_cavalier_epreuve_practice PRIMARY KEY (cavalier_epreuve_practice_id)
);

CREATE TABLE epreuve
(
    epreuve_id         BIGINT                                    NOT NULL,
    discipline_id      BIGINT,
    name               VARCHAR(255),
    qualification      JSONB,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by   VARCHAR(255),
    CONSTRAINT pk_epreuve PRIMARY KEY (epreuve_id)
);

ALTER TABLE cavalier_epreuve_participated
    ADD CONSTRAINT FK_CAVALIER_EPREUVE_PARTICIPATED_ON_CAVALIER FOREIGN KEY (cavalier_id) REFERENCES cavalier (cavalier_id);

ALTER TABLE cavalier_epreuve_participated
    ADD CONSTRAINT FK_CAVALIER_EPREUVE_PARTICIPATED_ON_EPREUVE FOREIGN KEY (epreuve_id) REFERENCES epreuve (epreuve_id);

ALTER TABLE cavalier_epreuve_practice
    ADD CONSTRAINT FK_CAVALIER_EPREUVE_PRACTICE_ON_CAVALIER FOREIGN KEY (cavalier_id) REFERENCES cavalier (cavalier_id);

ALTER TABLE cavalier_epreuve_practice
    ADD CONSTRAINT FK_CAVALIER_EPREUVE_PRACTICE_ON_EPREUVE FOREIGN KEY (epreuve_id) REFERENCES epreuve (epreuve_id);

ALTER TABLE epreuve
    ADD CONSTRAINT FK_EPREUVE_ON_DISCIPLINE FOREIGN KEY (discipline_id) REFERENCES discipline (discipline_id);


CREATE TABLE team
(
    team_id            BIGINT                                    NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by   VARCHAR(255),
    CONSTRAINT pk_team PRIMARY KEY (team_id)
);

ALTER TABLE cavalier_team_participated
    ADD CONSTRAINT FK_CAVALIER_TEAM_PARTICIPATED_ON_CAVALIER FOREIGN KEY (cavalier_id) REFERENCES cavalier (cavalier_id);

ALTER TABLE cavalier_team_participated
    ADD CONSTRAINT FK_CAVALIER_TEAM_PARTICIPATED_ON_TEAM FOREIGN KEY (team_id) REFERENCES team (team_id);

ALTER TABLE epreuve_team_participated
    ADD CONSTRAINT FK_EPREUVE_TEAM_PARTICIPATED_ON_EPREUVE FOREIGN KEY (epreuve_id) REFERENCES epreuve (epreuve_id);

ALTER TABLE epreuve_team_participated
    ADD CONSTRAINT FK_EPREUVE_TEAM_PARTICIPATED_ON_TEAM FOREIGN KEY (team_id) REFERENCES team (team_id);