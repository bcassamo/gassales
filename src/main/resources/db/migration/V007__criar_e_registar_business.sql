CREATE TABLE business (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo_business VARCHAR(20) NOT NULL,
    descricao VARCHAR(20) NOT NULL,
    data_business DATE NOT NULL,
    id_lancamento BIGINT NOT NULL,
    FOREIGN KEY (id_lancamento) REFERENCES lancamento(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO business (codigo_business, descricao, data_business, id_lancamento) values ('20210608-001A', 'Aquisição', '2021-06-08', 1);
INSERT INTO business (codigo_business, descricao, data_business, id_lancamento) values ('20210608-001A', 'Aquisição', '2021-06-08', 2);
INSERT INTO business (codigo_business, descricao, data_business, id_lancamento) values ('20210608-002V','Venda', '2021-06-08', 3);