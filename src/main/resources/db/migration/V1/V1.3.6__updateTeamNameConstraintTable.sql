ALTER TABLE IF EXISTS team
    DROP CONSTRAINT team_name_lower_check;

CREATE OR REPLACE FUNCTION check_team_name_unique()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS(
            SELECT 1
            FROM team
            WHERE lower(name) = lower(NEW.name)
              AND team_id <> NEW.team_id
        ) THEN
        RAISE EXCEPTION 'Violation of unique name constraint (name: %)',NEW.name USING ERRCODE = '23505';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER team_name_unique_trigger
    BEFORE INSERT OR UPDATE
    ON team
    FOR EACH ROW
EXECUTE FUNCTION check_team_name_unique();

-- ALTER TABLE IF EXISTS team
--     ADD COLUMN name_lower text;
--
-- UPDATE team
-- SET name_lower = lower(name);
--
-- ALTER TABLE IF EXISTS team
--     ADD CONSTRAINT team_name_lower_check UNIQUE (name_lower);