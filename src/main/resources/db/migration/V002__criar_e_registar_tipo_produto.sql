CREATE TABLE tipo_produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(50) NOT NULL,
    id_unidade_medida BIGINT NOT NULL,
    FOREIGN KEY (id_unidade_medida) REFERENCES unidade_medida(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO tipo_produto (descricao, id_unidade_medida) values ('Botijas de Gás', 2);
INSERT INTO tipo_produto (descricao, id_unidade_medida) values ('Líquidos', 1);