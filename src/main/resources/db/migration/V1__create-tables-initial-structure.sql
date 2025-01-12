CREATE TABLE usuarios
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome  VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE perfis
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE usuarios_perfis
(
    usuario_id BIGINT NOT NULL,
    perfil_id  BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT FK_Usuarios_Perfis_Usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT FK_Usuarios_Perfis_Perfil FOREIGN KEY (perfil_id) REFERENCES perfis (id)
);

CREATE TABLE cursos
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL
);

CREATE TABLE topicos
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo       VARCHAR(255)  NOT NULL,
    mensagem     VARCHAR(5000) NOT NULL, -- Seguindo o Fórum Alura original
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    status       VARCHAR(50)   NOT NULL,
    usuario_id   BIGINT,
    curso_id     BIGINT,
    CONSTRAINT FK_Topico_Autor FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT FK_Topico_Curso FOREIGN KEY (curso_id) REFERENCES cursos (id)
);

CREATE TABLE respostas
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    mensagem     TEXT NOT NULL,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    topico_id    BIGINT,
    usuario_id   BIGINT,
    solucao      BOOLEAN  DEFAULT FALSE,
    CONSTRAINT FK_Resposta_Topico FOREIGN KEY (topico_id) REFERENCES topicos (id),
    CONSTRAINT FK_Resposta_Autor FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);
