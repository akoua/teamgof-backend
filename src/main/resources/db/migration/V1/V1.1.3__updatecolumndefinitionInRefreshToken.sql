ALTER TABLE IF EXISTS refresh_token
    ALTER COLUMN expiry_date SET DATA TYPE TIMESTAMP WITH TIME ZONE USING expiry_date AT TIME ZONE 'utc',
    ALTER COLUMN expiry_date SET NOT NULL;