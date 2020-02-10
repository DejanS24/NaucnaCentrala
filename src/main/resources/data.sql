insert into upp_nc.user (dtype, id, city, email, name, password, state, surname, token, username, magazine_id, is_admin) values ('User', 995, 'Ns', 'deja@gmail.com', 'Dejan', '$2a$10$3U98gKM8r7PneVfLMx3gAusqXXvxcz68nywc2fe4t9XDuJoLe5O1W', 'Active', 'S', NULL, 'deja', NULL, false);
insert into upp_nc.user (dtype, id, city, email, name, password, state, surname, token, username, magazine_id, is_admin) values ('Reviewer', 994, 'Ns', 'pera@gmail.com', 'Pera', '$2a$10$3U98gKM8r7PneVfLMx3gAusqXXvxcz68nywc2fe4t9XDuJoLe5O1W', 'Active', 'P', NULL, 'pera', NULL, false);
insert into upp_nc.user (dtype, id, city, email, name, password, state, surname, token, username, magazine_id, is_admin) values ('Editor', 993, 'Ns', 'mike@gmail.com', 'Mika', '$2a$10$3U98gKM8r7PneVfLMx3gAusqXXvxcz68nywc2fe4t9XDuJoLe5O1W', 'Active', 'M', NULL, 'mika', 999, false);

insert into upp_nc.magazine (id, is_open_access, issn_number, name, chief_editor_id) values (999, true, 'm1', 'Magazine Physics', 993);

insert into upp_nc.magazine_reviewers (magazine_id, reviewers_id) values (999, 994);