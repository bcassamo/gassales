ALTER TABLE business ADD COLUMN finalizado BOOLEAN NOT NULL;

UPDATE business SET finalizado = true where id = 1;
UPDATE business SET finalizado = true where id = 2;
UPDATE business SET finalizado = true where id = 3;
UPDATE business SET finalizado = true where id = 4;