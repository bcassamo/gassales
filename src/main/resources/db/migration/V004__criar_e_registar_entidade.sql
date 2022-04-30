CREATE TABLE entidade (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     nome VARCHAR(50) NOT NULL,
     nuit VARCHAR(10),
     tipo VARCHAR(20) NOT NULL,
     bairro VARCHAR(50),
     rua VARCHAR(50),
     numero VARCHAR(10)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO entidade (nome, nuit, tipo, bairro, rua, numero) values ('Almeida Costa', '1109332', 'CLIENTE','Bairro de Muhalaze', 'Rua do Limpopo', '132A');
INSERT INTO entidade (nome, nuit, tipo, bairro, rua, numero) values ('Betuel Fernando', '1015339', 'CLIENTE', null, null, null);
INSERT INTO entidade (nome, nuit, tipo, bairro, rua, numero) values ('MOGAS LDA', '10851050', 'FORNECEDOR', 'Bairro do Jardim', 'Av. Moçambique', '864');