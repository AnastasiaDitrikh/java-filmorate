package ru.yandex.practicum.filmorate.storage.h2.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс GenreMapper является маппером для преобразования строк из результирующего набора ResultSet в объекты типа Genre.
 * Варианты исключений, которые могут быть выброшены:
 * - SQLException: возникает, если происходит ошибка при доступе к данным SQL.
 */
@Component
public class GenreMapper implements RowMapper<Genre> {

    /**
     * Метод mapRow(ResultSet rs, int rowNum) преобразует строку результирующего набора ResultSet в объект типа Genre.
     *
     * @param rs     результирующий набор ResultSet
     * @param rowNum номер строки результата
     * @return объект Genre, созданный из строки результирующего набора
     * @throws SQLException возникает, если происходит ошибка при доступе к данным SQL
     */
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}