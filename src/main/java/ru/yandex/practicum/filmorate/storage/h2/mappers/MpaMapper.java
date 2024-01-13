package ru.yandex.practicum.filmorate.storage.h2.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс MpaMapper является маппером для преобразования строк из результирующего набора ResultSet в объекты типа Mpa.
 * Варианты исключений, которые могут быть выброшены:
 * - SQLException: возникает, если происходит ошибка при доступе к данным SQL.
 */
@Component
public class MpaMapper implements RowMapper<Mpa> {

    /**
     * Метод mapRow(ResultSet rs, int rowNum) преобразует строку результирующего набора ResultSet в объект типа Mpa.
     *
     * @param rs     результирующий набор ResultSet
     * @param rowNum номер строки результата
     * @return объект Mpa, созданный из строки результирующего набора
     * @throws SQLException возникает, если происходит ошибка при доступе к данным SQL
     */
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder().id(rs.getLong("id")).name(rs.getString("name")).build();
    }
}