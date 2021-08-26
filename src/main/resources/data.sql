delete
from users_roles;
delete
from users;
delete
from role;


insert into role (name)
values ('ADMIN'),
       ('USER');

insert into users (username, password)
values ('admin', '$2a$10$UuLpIzZTRiDcnE1rRtory.y9/fx70suc3RzS0Fee.kqGm8YHEon9i');

INSERT INTO users_roles
    (user_id, roles_id)
VALUES ((select id from users where username = 'admin'),
        (select id from role where name = 'ADMIN'));

delete
from good
where description like '%test_data%';

insert into good(name, description, price, quantity)
values ('good 5', 'description 5 test_data', 500, 1),
       ('good 1', 'description 1 test_data', 5.5, 10),
       ('good 2', 'description 2 test_data', 25.5, 100),
       ('good 3', 'description 3 test_data', 55.5, 1000);