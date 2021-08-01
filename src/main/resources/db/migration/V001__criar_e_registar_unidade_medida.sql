CREATE TABLE unidade_medida (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(50) NOT NULL,
    siglas VARCHAR(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO unidade_medida (descricao, siglas) VALUES ('Litro', 'L');
INSERT INTO unidade_medida (descricao, siglas) VALUES ('Kilograma', 'Kg');