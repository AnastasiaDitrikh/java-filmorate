package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface LikesDao {
  void addLike(Long filmId, Long userId);

  void deleteLike(Long filmId, Long userId);


  Collection<Long> collectLikeByFilmId(Long filmId);
}
