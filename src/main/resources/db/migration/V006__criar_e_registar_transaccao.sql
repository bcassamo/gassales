CREATE TABLE transaccao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_transaccao VARCHAR(20) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    data_transaccao DATE NOT NULL,
    id_entidade BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    estado_produto VARCHAR(10) NOT NULL,
    quantidade BIGINT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_entidade) REFERENCES entidade(id),
    FOREIGN KEY (id_produto) REFERENCES produto(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO transaccao (id_transaccao, tipo, data_transaccao, id_entidade, id_produto, estado_produto, quantidade, valor_total) values ('20220717-001A', 'Aquisição', '2022-07-17', 3, 1, 'CHEIA', 10, 100000.00);
INSERT INTO transaccao (id_transaccao, tipo, data_transaccao, id_entidade, id_produto, estado_produto, quantidade, valor_total) values ('20220717-001A', 'Aquisição', '2022-07-17', 3, 2, 'VAZIA', 10, 20000.00);
INSERT INTO transaccao (id_transaccao, tipo, data_transaccao, id_entidade, id_produto, estado_produto, quantidade, valor_total) values ('20220717-002V', 'Venda', '2022-07-17', 1, 1, 'CHEIA', 2, 2000.00);