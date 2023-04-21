package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FilmDao;
import ru.yandex.practicum.filmorate.storage.GenreDao;
import ru.yandex.practicum.filmorate.storage.LikesDao;
import ru.yandex.practicum.filmorate.storage.MpaDao;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmDao filmDao;
    private final UserService userService;
    private final GenreService genreService;
    private final GenreDao genreDao;
    private final MpaDao mpaDao;
    private final LikesDao likeDao;

    public FilmService(@Qualifier("filmH2") FilmDao filmDao, UserService userService, GenreService genreService, GenreDao genreDao, MpaDao mpaDao, LikesDao likeDao) {
        this.filmDao = filmDao;
        this.userService = userService;
        this.genreService = genreService;
        this.genreDao = genreDao;
        this.mpaDao = mpaDao;
        this.likeDao = likeDao;
    }

    public List<Film> findAll() {
        final List<Film> films = filmDao.findAll();
        for (Film film : films) {
            final List<Genre> genresByFilmId = genreService.findGenresByFilmId(film.getId());
            film.setGenres(genresByFilmId);
            mpaDao.getMpaById(film.getMpa().getId()).ifPresent(film::setMpa);
        }
        return films;
    }

    public Film getFilmById(Long filmId) {
        final Film foundedFilm = filmDao
                .getFilmById(filmId)
                .orElseThrow(()
                        -> new NotFoundException("Фильма с id = " + filmId + "нет в базе"));
        foundedFilm.setGenres(genreDao.findGenresByFilmId(filmId));
        mpaDao.getMpaById(foundedFilm.getMpa().getId()).ifPresent(foundedFilm::setMpa);
        return foundedFilm;
    }

    public Film add(Film film) {
        filmDao.add(film);
        if (!film.getGenres().isEmpty()) {
            Set<Genre> genres = new HashSet<>(film.getGenres());
            genres.forEach(genre -> genreDao.updateFilmGenre(film.getId(), genre.getId()));

            final List<Genre> collected = new ArrayList<>(genres).stream()
                    .sorted((Comparator.comparingLong(Genre::getId)))
                    .collect(Collectors.toList());
            film.setGenres(collected);
        }
        return film;
    }

    public Film update(final Film film) {
        filmDao
                .getFilmById(film.getId())
                .orElseThrow(()
                        -> new NotFoundException("Фильма с id = " + film.getId() + "нет в базе"));
        final Film updatedFilm = filmDao.update(film);

        if (!film.getGenres().isEmpty()) {
            Set<Genre> genres = new HashSet<>(film.getGenres());
            genreDao.deleteAllGenresByFilmId(film.getId());

            genres.forEach(genre -> genreDao.updateFilmGenre(updatedFilm.getId(), genre.getId()));

            final List<Genre> collected = new ArrayList<>(genres).stream()
                    .sorted((Comparator.comparingLong(Genre::getId)))
                    .collect(Collectors.toList());
            updatedFilm.setGenres(collected);
        } else {
            genreDao.deleteAllGenresByFilmId(film.getId());
        }

        return updatedFilm;
    }

    public Film addLike(Long filmId, Long userId) {
        Film film = this.getFilmById(filmId);
        userService.getUserById(userId);
        likeDao.addLike(filmId, userId);
        return film;
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = this.getFilmById(filmId);
        userService.getUserById(userId);

        likeDao.deleteLike(filmId, userId);
    }

    public List<Film> getTheMostPopularMovies(int count) {
        Collection<Film> allFilms = filmDao.findAll();

        allFilms.forEach(film -> {
            final Collection<Long> collectedFilmsId = likeDao.collectLikeByFilmId(film.getId());
            film.setLikes(new HashSet<>(collectedFilmsId));

            final List<Genre> genresByFilmId = genreService.findGenresByFilmId(film.getId());
            film.setGenres(genresByFilmId);
            mpaDao.getMpaById(film.getMpa().getId()).ifPresent(film::setMpa);
        });

        return allFilms.stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
