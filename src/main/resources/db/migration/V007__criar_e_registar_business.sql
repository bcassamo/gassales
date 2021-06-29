CREATE TABLE business (
    id VARCHAR(20) PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL,
    data_business DATE NOT NULL,
    valor_business DECIMAL(10, 2) NOT NULL,
    id_entidade BIGINT(20) NOT NULL,
    FOREIGN KEY (id_entidade) REFERENCES entidade(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO business (id, descricao, data_business, valor_business, id_entidade) values ('20210608-001A', 'Aquisição', '2021-06-08', 5045.00, 5);
INSERT INTO business (id, descricao, data_business, valor_business, id_entidade) values ('20210608-002V','Venda', '2021-06-08', 3200.00, 1);