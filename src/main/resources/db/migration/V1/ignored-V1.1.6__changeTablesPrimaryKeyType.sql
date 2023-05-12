ALTER TABLE IF EXISTS discipline
    ALTER COLUMN discipline_id SET DATA TYPE SERIAL USING discipline_id::SERIAL,
    ALTER COLUMN discipline_id SET NOT NULL;

