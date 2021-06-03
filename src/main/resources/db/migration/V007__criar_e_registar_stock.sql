CREATE TABLE stock (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    quantidade BIGINT(20) NOT NULL,
    id_produto BIGINT(20) NOT NULL,
    id_filial BIGINT(20) NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO stock (quantidade, id_produto, id_filial) values (8, 1, 1);
INSERT INTO stock (quantidade, id_produto, id_filial) values (5, 2, 1);
INSERT INTO stock (quantidade, id_produto, id_filial) values (0, 3, 2);
INSERT INTO stock (quantidade, id_produto, id_filial) values (20, 4, 2);