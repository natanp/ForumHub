INSERT INTO usuarios (nome,login,senha,email)
VALUES ('Administrador','admin','$2a$12$CcNMIwr65gZxqocD9W4ODuqnbMX1MabLaLi2sJlMd794n8/A6pJFy','admin@email.com'),
       ('Usuário','usuario','$2a$12$CcNMIwr65gZxqocD9W4ODuqnbMX1MabLaLi2sJlMd794n8/A6pJFy','usuario@email.com');

INSERT INTO usuarios_perfis (usuario_id,perfil_id)
VALUES (1,1),
       (2,2);
