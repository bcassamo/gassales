ALTER TABLE lancamento ADD COLUMN id_entidade BIGINT NOT NULL;
UPDATE lancamento SET id_entidade = 3 where id = 1;
UPDATE lancamento SET id_entidade = 3 where id = 2;
UPDATE lancamento SET id_entidade = 1 where id = 3;

ALTER TABLE business DROP FOREIGN KEY business_ibfk_2;
ALTER TABLE business DROP COLUMN id_entidade;

ALTER TABLE lancamento ADD FOREIGN KEY (id_entidade) REFERENCES entidade(id);