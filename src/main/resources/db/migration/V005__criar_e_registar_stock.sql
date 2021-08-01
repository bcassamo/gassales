CREATE TABLE stock (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quantidade BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO stock (quantidade, id_produto) values (10, 1);
INSERT INTO stock (quantidade, id_produto) values (5, 2);
