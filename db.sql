CREATE TABLE movies (
    id    integer PRIMARY KEY NOT NULL,
    title varchar(40) NOT NULL
);

CREATE TABLE actors (
     id   integer PRIMARY KEY NOT NULL,
     name varchar(40) NOT NULL CHECK (name <> '')
);

CREATE TABLE movies_actors (
     id       SERIAL PRIMARY KEY,
     actor_id integer NOT NULL,
     movie_id integer NOT NULL,
     FOREIGN KEY (actor_id) REFERENCES actors (id),
     FOREIGN KEY (movie_id) REFERENCES movies (id)
);

INSERT INTO movies(id, title) VALUES(1, 'Die Hard');
INSERT INTO movies(id, title) VALUES(2, 'Matrix');
INSERT INTO movies(id, title) VALUES(3, 'Footloose');

INSERT INTO actors(id, name) VALUES(1, 'Kevin Bacon');
INSERT INTO actors(id, name) VALUES(2, 'Bruce Willis');
INSERT INTO actors(id, name) VALUES(3, 'Keanu Reeves');
INSERT INTO actors(id, name) VALUES(4, 'John Lithgow');

INSERT INTO movies_actors(actor_id, movie_id) VALUES(1, 3);
INSERT INTO movies_actors(actor_id, movie_id) VALUES(4, 3);
INSERT INTO movies_actors(actor_id, movie_id) VALUES(2, 1);
INSERT INTO movies_actors(actor_id, movie_id) VALUES(3, 2);
