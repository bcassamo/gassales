CREATE TABLE stock (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    quantidade BIGINT(20) NOT NULL,
    id_produto BIGINT(20) NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO stock (quantidade, id_produto) values (10, 1);
INSERT INTO stock (quantidade, id_produto) values (5, 2);
