package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FilmDao;
import ru.yandex.practicum.filmorate.storage.GenreDao;
import ru.yandex.practicum.filmorate.storage.LikesDao;
import ru.yandex.practicum.filmorate.storage.validators.ValidatorFilm;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmDao filmDao;
    private final UserService userService;
    private final GenreService genreService;
    private final GenreDao genreDao;
    private final LikesDao likeDao;

    public FilmService(FilmDao filmDao, UserService userService, GenreService genreService, GenreDao genreDao, LikesDao likeDao) {
        this.filmDao = filmDao;
        this.userService = userService;
        this.genreService = genreService;
        this.genreDao = genreDao;
        this.likeDao = likeDao;
    }

    public List<Film> findAll() {
        final List<Film> films = filmDao.findAll();
        for (Film film : films) {
            final List<Genre> genresByFilmId = new ArrayList<>(genreService.findGenresByFilmId(film.getId()));
            film.setGenres(genresByFilmId);
        }
        return films;
    }

    public Film getFilmById(Long filmId) {
        final Film foundedFilm = filmDao
                .getFilmById(filmId)
                .orElseThrow(()
                        -> new NotFoundException("Фильма с id = " + filmId + "нет в базе"));
        foundedFilm.setGenres(genreDao.findGenresByFilmId(filmId));
        return foundedFilm;
    }

    public Film add(Film film) {
        ValidatorFilm.validateFilm(film);
        filmDao.add(film);
        if (!film.getGenres().isEmpty()) {
            Set<Genre> genres = new HashSet<>(film.getGenres());
            genreDao.addGenresToFilm(film, genres);
            film.setGenres(sortGenres(genres));
        }
        return film;
    }


    public Film update(final Film film) {
        ValidatorFilm.validateFilm(film);
        filmDao
                .getFilmById(film.getId())
                .orElseThrow(()
                        -> new NotFoundException("Фильма с id = " + film.getId() + "нет в базе"));


        final Film updatedFilm = filmDao.update(film);

        if (!film.getGenres().isEmpty()) {
            Set<Genre> genres = new HashSet<>(film.getGenres());
            genreDao.deleteAllGenresByFilmId(film.getId());
            genreDao.addGenresToFilm(film, genres);
            updatedFilm.setGenres(sortGenres(genres));
        } else {
            genreDao.deleteAllGenresByFilmId(film.getId());
        }

        return updatedFilm;
    }


    public void addLike(Long filmId, Long userId) {
        likeDao.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = this.getFilmById(filmId);
        userService.getUserById(userId);
        likeDao.deleteLike(filmId, userId);
    }


    public Collection<Film> getMostPopularMovies(int count) {
        return filmDao.getMostPopularMovies(count);
    }


    private List<Genre> sortGenres(Set<Genre> genres) {
        return new ArrayList<>(genres).stream()
                .sorted((Comparator.comparingLong(Genre::getId)))
                .collect(Collectors.toList());
    }
}
