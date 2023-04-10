package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmServiceInterface {

    Film addLike(Long userId, Long filmId);

    void deleteLike(Long userId, Long filmId);

    List<Film> getTheMostPopularMovies(int count);
}



