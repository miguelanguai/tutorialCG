INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES ('John Doe');
INSERT INTO client(name) VALUES ('Lewis Aguirre');
INSERT INTO client(name) VALUES ('Adam Francis');
INSERT INTO client(name) VALUES ('Rosalind Espinoza');
INSERT INTO client(name) VALUES ('Ray Ruiz');
INSERT INTO client(name) VALUES ('Lorna Wilcox');
INSERT INTO client(name) VALUES ('Maximus Molina');
INSERT INTO client(name) VALUES ('Lucie Randolph');
INSERT INTO client(name) VALUES ('Chloe Schmidt');
INSERT INTO client(name) VALUES ('Hugo Pitts');

/*Las fechas necesitan estar en +YYYYMMDD para que el parseador las coja bien, si no salta un error*/
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 1, '+2023-01-04', '+2023-01-15');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (4, 1, '+2023-03-04', '+2023-03-09');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 2, '+2023-01-31', '+2023-02-12');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 3, '+2023-05-12', '+2023-05-20');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (3, 3, '+2023-04-15', '+2023-04-19');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (5, 3, '+2023-02-02', '+2023-02-16');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (6, 3, '+2023-07-16', '+2023-07-30');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (1, 4, '+2023-07-06', '+2023-07-11');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (1, 5, '+2023-11-22', '+2023-11-30');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 5, '+2023-07-24', '+2023-08-05');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (4, 5, '+2023-07-01', '+2023-07-11');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 6, '+2023-10-10', '+2023-10-24');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (3, 7, '+2023-08-30', '+2023-09-08');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (5, 7, '+2023-04-21', '+2023-05-01');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 8, '+2023-12-12', '+2023-12-24');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (6, 8, '+2023-09-08', '+2023-09-21');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (4, 9, '+2023-11-04', '+2023-11-18');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (1, 10, '+2023-12-04', '+2023-12-18');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (5, 10, '+2023-12-04', '+2024-12-10');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (6, 10, '+2024-01-20', '+2024-02-02');