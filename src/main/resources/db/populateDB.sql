DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, dateTime, user_id)
VALUES ('Завтрак', 1000, '2017-02-01 02:00:00', 100000);

INSERT INTO meals (description, calories, dateTime, user_id)
VALUES ('Обед', 500, '2017-01-01 13:00:00', 100000);

INSERT INTO meals (description, calories, dateTime, user_id)
VALUES ('Ужин', 1500, '2017-01-01 22:00:00',100000);

INSERT INTO meals (description, calories, dateTime, user_id)
VALUES ('Завтрак', 1000, '2017-01-01 09:00:00', 100001);

INSERT INTO meals (description, calories, dateTime, user_id)
VALUES ('Обед', 500, '2017-01-01 14:00:00',100001);

INSERT INTO meals (description, calories, dateTime, user_id)
VALUES ('Ужин', 1500, '2017-01-01 21:00:00', 100001);

