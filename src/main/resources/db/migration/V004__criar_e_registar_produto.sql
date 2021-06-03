CREATE TABLE produto (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    designacao VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    tamanho DECIMAL(10,2) NOT NULL,
    id_tipo BIGINT(20) NOT NULL,
    FOREIGN KEY (id_tipo) REFERENCES tipo_produto(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto (designacao, descricao, tamanho, id_tipo) values ('Oxigénio', 'Botija de Oxigénio', 8, 1);
INSERT INTO produto (designacao, descricao, tamanho, id_tipo) values ('Couve', null, 0, 3);