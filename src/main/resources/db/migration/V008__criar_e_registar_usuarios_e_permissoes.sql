CREATE TABLE utilizador (
    id BIGINT(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
    id BIGINT(20) PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE utilizador_permissao (
    id_utilizador BIGINT(20) NOT NULL,
    id_permissao BIGINT(20) NOT NULL,
    PRIMARY KEY (id_utilizador, id_permissao),
    FOREIGN KEY (id_utilizador) REFERENCES utilizador(id),
    FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO utilizador (id, nome, email, senha) values (1, 'Administrador', 'admin@peachsoftware.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO utilizador (id, nome, email, senha) values (2, 'Maria Silva', 'maria@peachsoftware.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permissao (id, descricao) values (1, 'ROLE_CADASTRAR_UNIDADES');
INSERT INTO permissao (id, descricao) values (2, 'ROLE_PESQUISAR_UNIDADES');

INSERT INTO permissao (id, descricao) values (3, 'ROLE_CADASTRAR_TIPO_PRODUTO');
INSERT INTO permissao (id, descricao) values (4, 'ROLE_REMOVER_TIPO_PRODUTO');
INSERT INTO permissao (id, descricao) values (5, 'ROLE_PESQUISAR_TIPO_PRODUTO');

INSERT INTO permissao (id, descricao) values (6, 'ROLE_CADASTRAR_PRODUTO');
INSERT INTO permissao (id, descricao) values (7, 'ROLE_REMOVER_PRODUTO');
INSERT INTO permissao (id, descricao) values (8, 'ROLE_PESQUISAR_PRODUTO');

INSERT INTO permissao (id, descricao) values (9, 'ROLE_CADASTRAR_ENTIDADE');
INSERT INTO permissao (id, descricao) values (10, 'ROLE_REMOVER_ENTIDADE');
INSERT INTO permissao (id, descricao) values (11, 'ROLE_PESQUISAR_ENTIDADE');

INSERT INTO permissao (id, descricao) values (12, 'ROLE_CADASTRAR_BUSINESS');
INSERT INTO permissao (id, descricao) values (13, 'ROLE_REMOVER_BUSINESS');
INSERT INTO permissao (id, descricao) values (14, 'ROLE_PESQUISAR_BUSINESS');

-- admin
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 1);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 2);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 3);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 4);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 5);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 6);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 7);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 8);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 9);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 10);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 11);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 12);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 13);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (1, 14);

-- maria
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (2, 2);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (2, 5);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (2, 8);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (2, 11);
INSERT INTO utilizador_permissao (id_utilizador, id_permissao) values (2, 14);