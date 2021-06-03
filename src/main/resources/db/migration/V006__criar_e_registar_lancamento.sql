CREATE TABLE lancamento (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(20) NOT NULL,
    quantidade BIGINT(20) NOT NULL,
    data_lancamento DATE NOT NULL,
    estado VARCHAR(10) NOT NULL,
    id_filial BIGINT(20) NOT NULL,
    id_produto BIGINT(20) NOT NULL,
    FOREIGN KEY (id_filial) REFERENCES filial(id),
    FOREIGN KEY (id_produto) REFERENCES produto(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, quantidade, data_lancamento, estado, id_filial, id_produto) values ('Inserção de Stock', 10, '2021-05-25', 'CHEIA', 1, 1);
INSERT INTO lancamento (descricao, quantidade, data_lancamento, estado, id_filial, id_produto) values ('Inserção de Stock', 3, '2021-05-25', 'VAZIA', 1, 2);