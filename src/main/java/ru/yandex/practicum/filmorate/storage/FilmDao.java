package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс FilmRepository предоставляет методы для работы с данными фильмов.
 * Методы:
 * - findAll: получает список всех фильмов из базы данных.
 * - add: добавляет новый фильм в базу данных и возвращает созданный фильм с присвоенным идентификатором.
 * - update: обновляет информацию о фильме в базе данных.
 * - getFilmById: получает фильм по его идентификатору.
 * - getMostPopularMovies: получает заданное количество самых популярных фильмов.
 */
public interface FilmDao {

    List<Film> findAll();

    Film add(Film film);

    Film update(Film film);

    Optional<Film> getFilmById(Long filmId);

    Collection<Film> getMostPopularMovies(int count);
}