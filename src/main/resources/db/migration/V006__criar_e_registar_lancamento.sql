CREATE TABLE lancamento (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo_business VARCHAR(20),
    descricao VARCHAR(20) NOT NULL,
    data_lancamento DATE NOT NULL,
    estado VARCHAR(10) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade BIGINT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    id_produto BIGINT NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produto(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO lancamento (codigo_business, descricao, data_lancamento, estado, preco, quantidade, valor_total, id_produto) values ('20220427-001A', 'Aquisição', '2022-04-27', 'CHEIA', 500.00, 10, 5000.00, 1);
INSERT INTO lancamento (codigo_business, descricao, data_lancamento, estado, preco, quantidade, valor_total, id_produto) values ('20220427-001A', 'Aquisição', '2022-04-27', 'VAZIA', 15.00, 3, 45.00, 2);
INSERT INTO lancamento (codigo_business, descricao, data_lancamento, estado, preco, quantidade, valor_total, id_produto) values ('20220427-002V', 'Venda', '2022-04-27', 'CHEIA', 640.00, 5, 3200.00, 1);