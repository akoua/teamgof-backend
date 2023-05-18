UPDATE epreuve
SET name = lower(name);

UPDATE team
SET name        = lower(name),
    departement = lower(departement);
