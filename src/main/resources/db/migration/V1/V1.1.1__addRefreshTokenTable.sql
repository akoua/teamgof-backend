CREATE TABLE IF NOT EXISTS refresh_token
(
    refresh_token_id   BIGINT                                    NOT NULL,
    cavalier_id        BIGINT                                    NOT NULL,
    token              VARCHAR(255)                              NOT NULL,
    expiry_date        TIMESTAMP WITHOUT TIME ZONE               NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by   VARCHAR(255),
    CONSTRAINT pk_refreshtoken PRIMARY KEY (refresh_token_id)
);

ALTER TABLE IF EXISTS refresh_token
    ADD CONSTRAINT uc_refreshtoken_token UNIQUE (token);

ALTER TABLE IF EXISTS refresh_token
    ADD CONSTRAINT FK_REFRESHTOKEN_ON_CAVALIER FOREIGN KEY (cavalier_id) REFERENCES cavalier (cavalier_id);