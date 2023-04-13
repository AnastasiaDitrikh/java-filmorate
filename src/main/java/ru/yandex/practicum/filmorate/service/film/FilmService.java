package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film add(Film film);

    Film update(Film film);

    Film addLike(Long userId, Long filmId);

    void deleteLike(Long userId, Long filmId);

    List<Film> getTheMostPopularMovies(int count);

    Film getFilmById(Long filmId);
}



