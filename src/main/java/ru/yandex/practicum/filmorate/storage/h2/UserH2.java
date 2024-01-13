package ru.yandex.practicum.filmorate.storage.h2;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDao;
import ru.yandex.practicum.filmorate.storage.h2.mappers.UserMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserH2 implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private static final String SQL_USERS_SELECT_ALL = "select * from users";
    private static final String SQL_USERS_SELECT_BY_ID = "select * from users where id = ?";
    private static final String SQL_INSERT_BY_ID = "insert into users (email, login, name,birthday) values (?, ?,?,?)";
    private static final String SQL_UPDATE_BY_ID = "update users set email = ?, login = ?, name = ? ,birthday = ? where id = ?";

    public UserH2(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    /**
     * Метод findAll возвращает список всех пользователей из базы данных.
     *
     * @return список объектов User
     */
    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_USERS_SELECT_ALL, userMapper);
    }

    /**
     * Метод add добавляет нового пользователя в базу данных.
     *
     * @param user объект User для добавления
     * @return объект User, добавленный в базу данных
     */
    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BY_ID, new String[]{"id"});
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));
            return preparedStatement;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    /**
     * Метод update обновляет информацию о пользователе в базе данных.
     *
     * @param user объект User для обновления
     * @return объект User, обновленный в базе данных
     */
    @Override
    public User update(User user) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    /**
     * Метод getUserById возвращает пользователя по его ID из базы данных.
     *
     * @param userId ID пользователя для извлечения
     * @return объект Optional, содержащий User, или пустой, если пользователь не найден
     */
    @Override
    public Optional<User> getUserById(Long userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_USERS_SELECT_BY_ID, userMapper, userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}