package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс GenreDao предоставляет методы для работы с жанрами фильмов в базе данных.
 * Возвращает все жанры из базы данных.
 * Возвращает жанр по его ID из базы данных.
 * Возвращает список жанров, связанных с фильмом по его ID из базы данных.
 * Обновляет жанр фильма в базе данных.
 * Добавляет набор жанров к фильму в базе данных.
 * Загружает жанры, связанные со списком фильмов, из базы данных.
 * Удаляет все жанры, связанные с фильмом, из базы данных.
 */
public interface GenreDao {

    Collection<Genre> getAllGenres();

    Optional<Genre> getGenreById(Long genreId);

    List<Genre> findGenresByFilmId(Long id);

    void updateFilmGenre(Long filmId, Long genreId);

    void addGenresToFilm(Film film, Set<Genre> listGenre);

    void load(List<Film> films);

    void deleteAllGenresByFilmId(Long filmId);
}