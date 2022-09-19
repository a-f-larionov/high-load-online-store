INSERT INTO role (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER')
    ON CONFLICT DO NOTHING;

INSERT INTO users (id, username, password)
VALUES (1, 'admin', '$2a$10$UuLpIzZTRiDcnE1rRtory.y9/fx70suc3RzS0Fee.kqGm8YHEon9i')
    ON CONFLICT DO NOTHING;

INSERT INTO users_roles
    (user_id, roles_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'),
        (SELECT id FROM role WHERE name = 'ADMIN'))
        ON CONFLICT DO NOTHING;

INSERT INTO good(id, name, description, price, quantity)
VALUES (1, 'Товар 1 Молоко', 'Молоко 1', 500, 1),
       (2, 'Товар 2 Кефир', 'Кефир 12', 5.5, 10),
       (3, 'Товар 3 Хлеб', 'Хлеб 3', 25.5, 100),
       (4, 'Товар 4 Соль', 'Соль 4', 55.5, 1000),
       (5, 'Товар 5 Сок Гранатовый', 'Сок Гранатовый 5', 55.5, 1000),
       (6, 'Товар 6 Сок Апельсиновый', 'Сок Апельсиновый 6', 55.5, 1000)
       ON CONFLICT DO NOTHING;