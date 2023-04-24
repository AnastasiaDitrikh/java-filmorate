package ru.yandex.practicum.filmorate.storage;

public interface LikesDao {
    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    Long collectLikeByFilmId(Long filmId);
}
