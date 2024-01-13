package ru.yandex.practicum.filmorate.storage.h2.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Класс UserMapper является маппером для преобразования строк из результирующего набора ResultSet в объекты типа User.
 * Варианты исключений, которые могут быть выброшены:
 * - SQLException: возникает, если происходит ошибка при доступе к данным SQL.
 */
@Component
public class UserMapper implements RowMapper<User> {

    /**
     * Метод mapRow(ResultSet rs, int rowNum) преобразует строку результирующего набора ResultSet в объект типа User.
     *
     * @param rs     результирующий набор ResultSet
     * @param rowNum номер строки результата
     * @return объект User, созданный из строки результирующего набора
     * @throws SQLException возникает, если происходит ошибка при доступе к данным SQL
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(LocalDate.parse(rs.getString("birthday")))
                .build();
    }
}