DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films_genres;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS mpa;

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT primary key auto_increment,
    email    varchar(50) NOT NULL,
    login    varchar(50) NOT NULL,
    name     varchar(50) NULL,
    birthday date        NOT NULL

);
create unique index if not exists USER_EMAIL_UINDEX on USERS (email);
create unique index if not exists USER_LOGIN_UINDEX on USERS (login);

CREATE TABLE IF NOT EXISTS mpa
(
    id   BIGINT primary key auto_increment,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    id   BIGINT primary key auto_increment,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    id           BIGINT primary key auto_increment,
    name         varchar(50)  NOT NULL,
    description  varchar(200) NOT NULL,
    release_date date         NOT NULL,
    duration     int          NOT NULL,
    mpa_id       bigint       NOT NULL REFERENCES mpa (id)
);

CREATE TABLE IF NOT EXISTS likes
(
    user_id bigint NOT NULL REFERENCES users (id),
    film_id bigint NOT NULL REFERENCES films (id),
    PRIMARY KEY (user_id, film_id)
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id       bigint  NOT NULL REFERENCES users (id),
    friend_id     bigint  NOT NULL REFERENCES users (id),
    friend_status boolean NOT NULL DEFAULT FALSE,
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS films_genres
(
    film_id  bigint NOT NULL REFERENCES films (id),
    genre_id bigint NOT NULL REFERENCES genres (id),
    PRIMARY KEY (film_id, genre_id)
);
