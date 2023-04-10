package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class FilmService implements FilmServiceInterface {

    @Autowired
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public Film addLike(Long userId, Long filmId) {
        Film film = filmStorage.getFilmById(filmId);
        film.getLikes().add(userId);
        filmStorage.update(film);
        log.info("Like к фильму с id = {} успешно добален", filmId);
        return film;
    }

    @Override
    public void deleteLike(Long userId, Long filmId) {
        Film film = filmStorage.getFilmById(filmId);
        if (!film.getLikes().contains(userId)) {
            log.info("Like к фильму с id = {} , от пользователя id={} не существует", filmId, userId);
            throw new NotFoundException("Like от пользователя id = " + userId + " не найден.");
        }
        film.getLikes().remove(userId);
        filmStorage.update(film);
        log.info("Like к фильму с id = {} успешно удален", film.getId());
    }

    @Override
    public List<Film> getTheMostPopularMovies(int count) {
        LikesComparator likesComparator = new LikesComparator();
        List<Film> mostPopularMovies = new ArrayList<>();
        List<Film> films = filmStorage.findAll();
        films.sort(likesComparator);
        if (films.size() < count) {
            count = films.size();
        }
        for (int i = 0; i < count; i++) {
            mostPopularMovies.add(films.get(i));
        }
        log.info("Список самых популярных фильмов получен");
        return mostPopularMovies;
    }

    private static class LikesComparator implements Comparator<Film> {
        @Override
        public int compare(Film o1, Film o2) {
            return o1.getLikes().size() - o2.getLikes().size();
        }
    }
}
