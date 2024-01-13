package ru.yandex.practicum.filmorate.storage.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDao;
import ru.yandex.practicum.filmorate.storage.h2.mappers.GenreMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;

@Component
@RequiredArgsConstructor
public class GenreH2 implements GenreDao {

    private final JdbcTemplate jdbcTemplate;
    private final GenreMapper genreMapper;

    private static final String SQL_GENRES_SELECT_ALL = "select * from genres";
    private static final String SQL_GENRE_SELECT_BY_ID = "select * " +
            "from genres " +
            "where id = ?";
    private static final String SQL_SELECT_GENRES_BY_FILM_ID = "SELECT g.id, g.name " +
            "FROM genres g " +
            "INNER JOIN films_genres fg ON g.id = fg.genre_id " +
            "WHERE fg.film_id = ?";
    private static final String SQL_INSERT_FILMS_GENRES = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";
    private static final String SQL_DELETE_FILMS_GENRES_BY_FILM_ID = "DELETE FROM films_genres WHERE film_id = ?";

    /**
     * Извлекает все жанры из базы данных.
     *
     * @return коллекция объектов Genre
     */
    @Override
    public Collection<Genre> getAllGenres() {
        return jdbcTemplate.query(SQL_GENRES_SELECT_ALL, genreMapper);
    }

    /**
     * Извлекает жанр по его ID из базы данных.
     *
     * @param genreId ID жанра для извлечения
     * @return объект Optional, содержащий Genre, или пустой, если не найден
     */
    @Override
    public Optional<Genre> getGenreById(Long genreId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SQL_GENRE_SELECT_BY_ID, genreMapper, genreId));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    /**
     * Извлекает список жанров, связанных с фильмом по его ID из базы данных.
     *
     * @param id ID фильма
     * @return список объектов Genre, связанных с фильмом
     */
    @Override
    public List<Genre> findGenresByFilmId(Long id) {
        return jdbcTemplate.query(SQL_SELECT_GENRES_BY_FILM_ID, genreMapper, id);
    }

    /**
     * Обновляет жанр фильма в базе данных.
     *
     * @param filmId  ID фильма
     * @param genreId ID жанра
     */
    @Override
    public void updateFilmGenre(Long filmId, Long genreId) {
        jdbcTemplate.update(SQL_INSERT_FILMS_GENRES, filmId, genreId);
    }

    /**
     * Добавляет набор жанров к фильму в базе данных.
     *
     * @param film      объект Film, к которому надо добавить жанры
     * @param listGenre набор объектов Genre для добавления
     */
    @Override
    public void addGenresToFilm(Film film, Set<Genre> listGenre) {
        List<Genre> genres = new ArrayList<>(listGenre);
        jdbcTemplate.batchUpdate(SQL_INSERT_FILMS_GENRES,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, film.getId());
                        ps.setLong(2, genres.get(i).getId());
                    }

                    public int getBatchSize() {
                        return listGenre.size();
                    }
                });
    }

    /**
     * Удаляет все жанры, связанные с фильмом, из базы данных.
     *
     * @param filmId ID фильма
     */
    @Override
    public void deleteAllGenresByFilmId(Long filmId) {
        jdbcTemplate.update(SQL_DELETE_FILMS_GENRES_BY_FILM_ID, filmId);
    }

    /**
     * Загружает жанры, связанные со списком фильмов, из базы данных.
     *
     * @param films список фильмов, для которых надо загрузить жанры
     */
    @Override
    public void load(List<Film> films) {
        final Map<Long, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, identity()));
        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final String sqlQuery = "select * " +
                "from GENRES g, films_genres fg " +
                "where fg.GENRE_ID = g.ID AND fg.FILM_ID in (" + inSql + ")";
        jdbcTemplate.query(sqlQuery, (rs) -> {
            final Film film = filmById.get(rs.getLong("FILM_ID"));
            film.getGenres().add(genreMapper.mapRow(rs, films.size()));
        }, films.stream().map(Film::getId).toArray());
    }
}