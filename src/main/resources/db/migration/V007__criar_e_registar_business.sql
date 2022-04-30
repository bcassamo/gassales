CREATE TABLE business (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo_business VARCHAR(20) NOT NULL,
    descricao VARCHAR(20) NOT NULL,
    data_business DATE NOT NULL,
    id_entidade BIGINT NOT NULL,
    finalizado BOOLEAN NOT NULL,
    FOREIGN KEY (id_entidade) REFERENCES entidade(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO business (codigo_business, descricao, data_business, id_entidade, finalizado) values ('20220427-001A', 'Aquisição', '2022-04-27', 3, true);
INSERT INTO business (codigo_business, descricao, data_business, id_entidade, finalizado) values ('20220427-001A', 'Aquisição', '2022-04-27', 3, true);
INSERT INTO business (codigo_business, descricao, data_business, id_entidade, finalizado) values ('20220427-002V','Venda', '2021-04-27', 1, true);