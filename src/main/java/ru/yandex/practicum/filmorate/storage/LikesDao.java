package ru.yandex.practicum.filmorate.storage;

/**
 * Интерфейс LikesDao предоставляет методы для управления "лайками" фильмов пользователей в базе данных.
 * Добавляет "лайк" к фильму от пользователя в базе данных.
 * Удаляет "лайк" к фильму от пользователя из базы данных.
 * Возвращает количество "лайков" для фильма по его ID из базы данных.
 */

public interface LikesDao {
    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    Long collectLikeByFilmId(Long filmId);
}