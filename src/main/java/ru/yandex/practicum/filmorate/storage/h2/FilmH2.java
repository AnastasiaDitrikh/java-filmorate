package ru.yandex.practicum.filmorate.storage.h2;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDao;
import ru.yandex.practicum.filmorate.storage.h2.mappers.FilmMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class FilmH2 implements FilmDao {

    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;

    private static final String SQL_FILMS_SELECT_ALL = "select f.*, m.name as mpa_name " +
            "from films as f  " +
            "join mpa as m on  f.mpa_id=m.id ";
    private static final String SQL_FILMS_SELECT_BY_ID = "select f.*, m.name as mpa_name " +
            "from films as f " +
            "join mpa as m on f.mpa_id=m.id where f.id = ?";
    private static final String SQL_INSERT_BY_ID = "insert into films (name, description, release_date,duration, mpa_id) values (?, ?,?,?,?)";
    private static final String SQL_UPDATE_BY_ID = "update films set name = ?, description = ?, release_date = ? ,duration = ?, mpa_id=? where id = ?";
    public static final String SELECT_POPULAR_FILMS = "SELECT f.*, m.name as mpa_name "
            + "FROM films as f "
            + "join mpa as m on  f.mpa_id=m.id "
            + "left join  LIKES as l on f.id=l.FILM_ID "
            + "group by f.id order by COUNT(l.USER_ID) desc limit ?";

    public FilmH2(JdbcTemplate jdbcTemplate, FilmMapper filmMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmMapper = filmMapper;
    }

    /**
     * Метод findAll() получает список всех фильмов из базы данных.
     *
     * @return список всех фильмов
     */
    @Override
    public List<Film> findAll() {
        return jdbcTemplate.query(SQL_FILMS_SELECT_ALL, filmMapper);
    }

    /**
     * Метод add(Film film) добавляет новый фильм в базу данных.
     *
     * @param film новый фильм
     * @return созданный фильм с присвоенным идентификатором
     */
    @Override
    public Film add(Film film) {
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

    /**
     * Метод update(film) обновляет информацию о фильме в базе данных.
     *
     * @param film фильм для обновления
     * @return обновленный фильм
     */
    @Override
    public Film update(Film film) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        return film;
    }

    /**
     * Метод getFilmById(Long filmId) получает фильм по его идентификатору.
     *
     * @param filmId идентификатор фильма
     * @return объект Optional с фильмом, если он найден, или пустой объект Optional, если фильм не найден
     */
    @Override
    public Optional<Film> getFilmById(Long filmId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FILMS_SELECT_BY_ID, filmMapper, filmId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Метод getMostPopularMovies(int count) получает заданное количество самых популярных фильмов.
     *
     * @param count количество фильмов
     * @return коллекция самых популярных фильмов
     */
    @Override
    public Collection<Film> getMostPopularMovies(int count) {
        return jdbcTemplate.query(SELECT_POPULAR_FILMS, filmMapper, count);
    }
}