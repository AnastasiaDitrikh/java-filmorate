package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenreDao {

  Collection<Genre> getAllGenres();

  Optional<Genre> getGenreById(Long genreId);

  List<Genre> findGenresByFilmId(Long id);

  void updateFilmGenre(Long filmId, Long genreId);

  void deleteAllGenresByFilmId(Long filmId);
}
