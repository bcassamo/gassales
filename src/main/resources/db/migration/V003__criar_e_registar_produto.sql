CREATE TABLE produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    tamanho DECIMAL(10,2) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    id_tipo_produto BIGINT NOT NULL,
    FOREIGN KEY (id_tipo_produto) REFERENCES tipo_produto(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO produto (nome, descricao, tamanho, preco, id_tipo_produto) values ('Oxigénio', 'Botija de Oxigénio', 8, 640.00, 1);
INSERT INTO produto (nome, descricao, tamanho, preco, id_tipo_produto) values ('Couve', null, 0, 50.00, 2);