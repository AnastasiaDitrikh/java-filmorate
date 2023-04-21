package ru.yandex.practicum.filmorate.storage.h2;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDao;
import ru.yandex.practicum.filmorate.storage.h2.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.storage.in.memory.ValidatorFilm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class FilmH2 implements FilmDao {

  private final JdbcTemplate jdbcTemplate;
  private final FilmMapper filmMapper;

  private static final String SQL_FILMS_SELECT_ALL = "select * from films";
  private static final String SQL_FILMS_SELECT_BY_ID = "select * from films where id = ?";
  private static final String SQL_INSERT_BY_ID = "insert into films (name, description, release_date,duration, mpa_id) values (?, ?,?,?,?)";
  private static final String SQL_UPDATE_BY_ID = "update films set name = ?, description = ?, release_date = ? ,duration = ?, mpa_id=? where id = ?";

  public FilmH2(JdbcTemplate jdbcTemplate, FilmMapper filmMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.filmMapper = filmMapper;
  }

  @Override
  public List<Film> findAll() {
    return jdbcTemplate.query(SQL_FILMS_SELECT_ALL, filmMapper);
  }

  @Override
  public Film add(Film film) {
    ValidatorFilm.validateFilm(film);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BY_ID, new String[]{"id"});
      preparedStatement.setString(1, film.getName());
      preparedStatement.setString(2, film.getDescription());
      preparedStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
      preparedStatement.setInt(4, film.getDuration());
      preparedStatement.setLong(5, film.getMpa().getId());
      return preparedStatement;
    }, keyHolder);
    film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    return film;
  }


  @Override
  public Film update(Film film) {
    ValidatorFilm.validateFilm(film);
    jdbcTemplate.update(SQL_UPDATE_BY_ID, film.getName(), film.getDescription(),
            film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
    return film;
  }

  @Override
  public Optional<Film> getFilmById(Long filmId) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FILMS_SELECT_BY_ID, filmMapper, filmId));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
