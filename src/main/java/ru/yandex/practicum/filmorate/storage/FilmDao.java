package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmDao {

    List<Film> findAll();

    Film add(Film film);

    Film update(Film film);

    Optional<Film> getFilmById(Long filmId);

    Collection<Film> getMostPopularMovies(int count);
}
