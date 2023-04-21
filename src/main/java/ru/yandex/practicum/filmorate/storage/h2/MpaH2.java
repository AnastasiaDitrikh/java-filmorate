package ru.yandex.practicum.filmorate.storage.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDao;
import ru.yandex.practicum.filmorate.storage.h2.mappers.MpaMapper;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaH2 implements MpaDao {

  private final JdbcTemplate jdbcTemplate;
  private final MpaMapper mpaMapper;

  private static final String SQL_MPA_SELECT_ALL = "select * from mpa";
  private static final String SQL_MPA_SELECT_BY_ID = "select * from mpa where id = ?";
  private static final String SELECT_MPA_BY_FILM_ID = "select m.id, m.name from mpa m inner join films f on f.mpa_id = m.id where f.id = ?";

  @Override
  public Collection<Mpa> findAllMpa() {
    return jdbcTemplate.query(SQL_MPA_SELECT_ALL, mpaMapper);
  }

  @Override
  public Optional<Mpa> getMpaById(Long mpaId) {
    try {
      return Optional.ofNullable(
              jdbcTemplate.queryForObject(SQL_MPA_SELECT_BY_ID, mpaMapper, mpaId));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Mpa> getMpaByFilmId(Long id) {
    try {
      return Optional.ofNullable(
              jdbcTemplate.queryForObject(SELECT_MPA_BY_FILM_ID, mpaMapper, id));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }
}
