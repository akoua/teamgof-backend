CREATE TABLE IF NOT EXISTS authority
(
    authority_id       BIGINT GENERATED BY DEFAULT AS IDENTITY   NOT NULL,
    cavalier_id        BIGINT,
    role               VARCHAR(255),
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    last_modified_by   VARCHAR(255),

    CONSTRAINT pk_authority PRIMARY KEY (authority_id)
);

ALTER TABLE authority
    ADD CONSTRAINT FK_AUTHORITY_ON_CAVALIER FOREIGN KEY (cavalier_id) REFERENCES cavalier (cavalier_id);