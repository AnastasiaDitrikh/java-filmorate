package ru.yandex.practicum.filmorate.storage.h2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserFriendDao;
import ru.yandex.practicum.filmorate.storage.h2.mappers.UserMapper;

import java.util.List;

@Component
public class UserFriendH2 implements UserFriendDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_INSERT_FRIEND = "insert into friends (user_id, friend_id) values (?, ?)";
    private static final String SQL_DELETE_FRIEND = "delete from friends where user_id = ? and friend_id = ?";

    private static final String SELECT_FRIENDS_BY_USER_ID =
            "SELECT u.* " +
                    "FROM users u " +
                    "RIGHT JOIN friends f ON u.id = f.friend_id " +
                    "WHERE f.user_id = ?";

    private static final String SELECT_COMMON_FRIENDS = "SELECT * FROM users u "
            + "JOIN friends AS f1 ON u.id = f1.friend_id "
            + "JOIN friends AS f2 ON u.id = f2.friend_id "
            + "WHERE f1.user_id = ? AND f2.user_id = ?";

    private static final String SELECT_FRIEND_BY_USER_ID = "SELECT u.* FROM users u"
            + " RIGHT JOIN friends f ON u.id = f.friend_id "
            + "WHERE f.user_id = ? AND f.friend_id = ?";

    public UserFriendH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод addFriend добавляет друга пользователю в базу данных.
     *
     * @param userId   ID пользователя
     * @param friendId ID друга
     */
    @Override
    public void addFriend(Long userId, Long friendId) {
        jdbcTemplate.update(SQL_INSERT_FRIEND, userId, friendId);
    }

    /**
     * Метод deleteFriend удаляет друга пользователя из базы данных.
     *
     * @param userId   ID пользователя
     * @param friendId ID друга
     */
    @Override
    public void deleteFriend(Long userId, Long friendId) {
        jdbcTemplate.update(SQL_DELETE_FRIEND, userId, friendId);
    }

    /**
     * Метод getFriendsByUserId возвращает список друзей пользователя по его ID из базы данных.
     *
     * @param id ID пользователя
     * @return список объектов User, представляющих друзей пользователя
     */
    @Override
    public List<User> getFriendsByUserId(Long id) {
        return jdbcTemplate.query(SELECT_FRIENDS_BY_USER_ID,
                new UserMapper(), id);
    }

    /**
     * Метод getCommonFriends возвращает список общих друзей между двумя пользователями по их ID из базы данных.
     *
     * @param userId   ID первого пользователя
     * @param friendId ID второго пользователя
     * @return список объектов User, представляющих общих друзей
     */
    @Override
    public List<User> getCommonFriends(Long userId, Long friendId) {

        return jdbcTemplate.query(SELECT_COMMON_FRIENDS,
                new UserMapper(), userId, friendId);
    }

    /**
     * Метод findFriends ищет друзей пользователей по их ID в базе данных.
     *
     * @param userId   ID первого пользователя
     * @param friendId ID второго пользователя
     * @return список объектов User, представляющих друзей пользователей
     */
    @Override
    public List<User> findFriends(Long userId, Long friendId) {
        return jdbcTemplate.query(
                SELECT_FRIEND_BY_USER_ID,
                new UserMapper(), userId, friendId);
    }
}