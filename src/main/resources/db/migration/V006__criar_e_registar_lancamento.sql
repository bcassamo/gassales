CREATE TABLE lancamento (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(20) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade BIGINT(20) NOT NULL,
    data_lancamento DATE NOT NULL,
    estado VARCHAR(10) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    id_produto BIGINT(20) NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, preco, quantidade, data_lancamento, estado, valor_total, id_produto) values ('Aquisição', 500.00, 10, '2021-06-06', 'CHEIA', 5000.00, 1);
INSERT INTO lancamento (descricao, preco, quantidade, data_lancamento, estado, valor_total, id_produto) values ('Aquisição', 15.00, 3,'2021-06-06', 'VAZIA', 45.00, 2);
INSERT INTO lancamento (descricao, preco, quantidade, data_lancamento, estado, valor_total, id_produto) values ('Venda', 640.00, 5, '2021-06-06', 'CHEIA', 3200.00, 1);