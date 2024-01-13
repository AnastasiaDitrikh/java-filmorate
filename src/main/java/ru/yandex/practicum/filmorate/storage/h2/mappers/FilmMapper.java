package ru.yandex.practicum.filmorate.storage.h2.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс FilmMapper является маппером для преобразования строк из результирующего набора ResultSet в объекты типа Film.
 * Варианты исключений, которые могут быть выброшены:
 * - SQLException: возникает, если происходит ошибка при доступе к данным SQL.
 */
@Component
public class FilmMapper implements RowMapper<Film> {

    /**
     * Метод mapRow(ResultSet rs, int rowNum) преобразует строку результирующего набора ResultSet в объект типа Film.
     *
     * @param rs     результирующий набор ResultSet
     * @param rowNum номер строки результата
     * @return объект Film, созданный из строки результирующего набора
     * @throws SQLException возникает, если происходит ошибка при доступе к данным SQL
     */
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .mpa(Mpa.builder().id((rs.getLong("mpa_id"))).name((rs.getString("mpa_name"))).build())
                .build();
    }
}