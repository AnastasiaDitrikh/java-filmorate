package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Интерфейс UserFriendDao предоставляет методы для управления друзьями пользователей в базе данных.
 * Добавляет друга пользователю в базу данных.
 * Удаляет друга пользователя из базы данных.
 * Возвращает список друзей пользователя по его ID из базы данных.
 * Возвращает список общих друзей между двумя пользователями по их ID.
 * Ищет друзей пользователей по их ID в базе данных.
 */
public interface UserFriendDao {
    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getFriendsByUserId(Long id);

    List<User> getCommonFriends(Long userId, Long friendId);

    List<User> findFriends(Long userId, Long friendId);
}