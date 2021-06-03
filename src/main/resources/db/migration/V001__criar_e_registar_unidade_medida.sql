CREATE TABLE unidade_medida (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(50) NOT NULL,
    siglas VARCHAR(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO unidade_medida (descricao, siglas) VALUES ('Litro', 'L');
INSERT INTO unidade_medida (descricao, siglas) VALUES ('Kilograma', 'Kg');