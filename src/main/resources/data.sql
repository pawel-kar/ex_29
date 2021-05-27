insert into user_role(role)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');
--
insert into user(username, password)
VALUES ('user', '{noop}user'),
        ('admin', '{noop}admin'),
       ('user2', '{noop}user'),
        ('user3','{noop}user'),
        ('admin2','{noop}admin');


insert into user_roles(user_id, roles_id)
VALUES (1,1),
       (2,2),
       (3,1),
       (4,1),
       (5,1),
       (5,2);