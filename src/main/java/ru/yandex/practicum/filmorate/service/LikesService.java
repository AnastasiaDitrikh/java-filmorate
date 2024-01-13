package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FilmDao;
import ru.yandex.practicum.filmorate.storage.LikesDao;
import ru.yandex.practicum.filmorate.storage.UserDao;

/**
 * Класс LikesService предоставляет методы для выполнения операций с лайками.
 */
@Service
public class LikesService {
    private final LikesDao likeDao;
    private final FilmDao filmDao;
    private final UserDao userDao;

    public LikesService(LikesDao likeDao, FilmDao filmDao, UserDao userDao) {
        this.likeDao = likeDao;
        this.filmDao = filmDao;
        this.userDao = userDao;
    }

    public void addLike(Long filmId, Long userId) {
        filmDao.getFilmById(filmId).orElseThrow(() -> new NotFoundException("Нет такого фильма"));
        userDao.getUserById(userId).orElseThrow(() -> new NotFoundException("Нет такого пользователя"));
        likeDao.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        filmDao.getFilmById(filmId).orElseThrow(() -> new NotFoundException("Нет такого фильма"));
        userDao.getUserById(userId).orElseThrow(() -> new NotFoundException("Нет такого пользователя"));
        likeDao.deleteLike(filmId, userId);
    }

    public Long collectLikeByFilmId(Long filmId) {
        return likeDao.collectLikeByFilmId(filmId);
    }
}