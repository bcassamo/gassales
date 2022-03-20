ALTER TABLE produto ADD COLUMN id_stock BIGINT NOT NULL;
UPDATE produto SET id_stock = 1 where id = 1;
UPDATE produto SET id_stock = 2 where id = 2;

ALTER TABLE stock DROP FOREIGN KEY stock_ibfk_1;
ALTER TABLE stock DROP COLUMN id_produto;

INSERT INTO stock (quantidade) values (0);
INSERT INTO stock (quantidade) values (0);

UPDATE produto SET id_stock = 3 where id = 3;
UPDATE produto SET id_stock = 4 where id = 4;

ALTER TABLE produto ADD FOREIGN KEY (id_stock) REFERENCES stock(id);