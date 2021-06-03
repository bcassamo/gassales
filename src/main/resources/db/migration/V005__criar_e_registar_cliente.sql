CREATE TABLE cliente (
     id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
     nome VARCHAR(50) NOT NULL,
     nuit INT NOT NULL,
     rua VARCHAR(50),
     numero VARCHAR(10),
     bairro VARCHAR(50),
     cidade VARCHAR(50),
     provincia VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cliente (nome, nuit, rua, numero, bairro, cidade, provincia) values ('Almeida Costa', '1109332', 'Rua do Limpopo', '132A', 'Bairro de Muhalaze', 'Cidade da Matola', 'Provincia de Maputo');
INSERT INTO cliente (nome, nuit, rua, numero, bairro, cidade, provincia) values ('Betuel Fernando', '1015339', null, null, null, null, null);