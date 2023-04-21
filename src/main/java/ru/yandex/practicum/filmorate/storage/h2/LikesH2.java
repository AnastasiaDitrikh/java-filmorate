package ru.yandex.practicum.filmorate.storage.h2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikesDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;


@Component
public class LikesH2 implements LikesDao {
    public static final String INSERT_LIKE_BY_FILM_ID_AND_USER_ID =
            "INSERT INTO likes ( film_id, user_id ) VALUES ( ?, ? )";

    public static final String DELETE_LIKE_BY_FILM_ID_AND_USER_ID =
            "DELETE FROM likes WHERE film_id =? AND user_id =?";

    public static final String SELECT_LIKES_BY_FILM_ID =
            "SELECT USER_ID FROM LIKES WHERE FILM_ID = ?";

    private final JdbcTemplate jdbcTemplate;

    public LikesH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void addLike(Long filmId, Long userId) {
        jdbcTemplate.update(INSERT_LIKE_BY_FILM_ID_AND_USER_ID, filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        jdbcTemplate.update(DELETE_LIKE_BY_FILM_ID_AND_USER_ID, filmId, userId);
    }

    @Override
    public Collection<Long> collectLikeByFilmId(Long filmId) {
        return jdbcTemplate.query(SELECT_LIKES_BY_FILM_ID, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("user_id");
            }
        }, filmId);
    }

}
