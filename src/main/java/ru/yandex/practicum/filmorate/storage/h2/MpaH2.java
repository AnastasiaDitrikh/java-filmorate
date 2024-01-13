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
    private static final String SELECT_MPA_BY_FILM_ID = "select m.id, m.name " +
            "from mpa m " +
            "inner join films f on f.mpa_id = m.id " +
            "where f.id = ?";

    /**
     * Метод findAllMpa извлекает все системы рейтингового ограничения фильмов из базы данных и возвращает их в виде коллекции объектов Mpa.
     *
     * @return коллекция объектов Mpa
     */
    @Override
    public Collection<Mpa> findAllMpa() {
        return jdbcTemplate.query(SQL_MPA_SELECT_ALL, mpaMapper);
    }

    /**
     * Метод getMpaById извлекает систему рейтингового ограничения фильма по ее ID из базы данных и возвращает ее в виде объекта Optional<Mpa>.
     * Если система рейтингового ограничения не найдена, возвращается пустой Optional.
     *
     * @param mpaId ID системы рейтингового ограничения для извлечения
     * @return объект Optional, содержащий Mpa, или пустой, если система рейтингового ограничения не найдена
     */
    @Override
    public Optional<Mpa> getMpaById(Long mpaId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SQL_MPA_SELECT_BY_ID, mpaMapper, mpaId));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    /**
     * Метод getMpaByFilmId извлекает систему рейтингового ограничения фильма по ID фильма из базы данных и возвращает ее в виде объекта Optional<Mpa>.
     * Если система рейтингового ограничения не найдена, возвращается пустой Optional.
     *
     * @param id ID фильма
     * @return объект Optional, содержащий Mpa, или пустой, если система рейтингового ограничения не найдена
     */
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