-- ---add case ignored in epreuve---

CREATE OR REPLACE FUNCTION check_nom_discipline_unique()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS(
            SELECT 1
            FROM epreuve
            WHERE lower(name) = lower(NEW.name)
              AND discipline_id = NEW.discipline_id
              AND epreuve_id <> NEW.epreuve_id
        ) THEN
        RAISE EXCEPTION 'Violation of unique name and discipline constraint (name: %, discipline: %)',NEW.name,NEW.discipline_id USING ERRCODE = '23505';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER name_discipline_unique_trigger
    BEFORE INSERT OR UPDATE
    ON epreuve
    FOR EACH ROW
EXECUTE FUNCTION check_nom_discipline_unique();


-- ---add case ignored in team ---

ALTER TABLE IF EXISTS team
    ADD CONSTRAINT team_name_lower_check CHECK ( name = lower(name) );

CREATE INDEX IF NOT EXISTS team_name_lower_index ON team (lower(name));


-- ---add case ignored in epreuve---
-- ALTER TABLE IF EXISTS epreuve
--     DROP COLUMN name_discipline_hash;
--
-- CREATE OR REPLACE FUNCTION lower_nom_discipline(name text, discipline bigint)
--     RETURNS text
--     LANGUAGE sql
--     IMMUTABLE
-- AS
-- $$
-- SELECT lower(name) || discipline;
-- $$;
--
-- ALTER TABLE IF EXISTS epreuve
--     ADD COLUMN IF NOT EXISTS name_discipline_hash bytea;
--
-- -- ALTER TABLE IF EXISTS epreuve
-- --     ADD COLUMN IF NOT EXISTS name_discipline_combined text GENERATED ALWAYS AS (lower_nom_discipline(name, discipline_id)) STORED;
--
-- -- UPDATE epreuve
-- -- SET name_discipline_combined = lower_nom_discipline(name, discipline_id);
--
-- UPDATE epreuve
-- SET name_discipline_hash = md5(lower_nom_discipline(name, discipline_id))::text::bytea;
-- -- SET name_discipline_hash = md5(name_discipline_combined)::text::bytea;
--
-- ALTER TABLE IF EXISTS epreuve
--     ADD CONSTRAINT name_discipline_hash_unique_uc UNIQUE (name_discipline_hash);
--
--
-- ---add case ignored in team ---
--
-- ALTER TABLE IF EXISTS team
--     DROP COLUMN name_hash;
--
-- ALTER TABLE IF EXISTS team
--     ADD COLUMN IF NOT EXISTS name_hash bytea;
--
-- UPDATE team
-- SET name_hash = md5(lower(name))::text::bytea;
--
-- ALTER TABLE IF EXISTS team
--     ADD CONSTRAINT name_hash_unique_uc UNIQUE (name_hash);
--
-- -- UPDATE epreuve
-- -- SET name = lower(name);
-- --
-- -- UPDATE team
-- -- SET name        = lower(name),
-- --     departement = lower(departement);
