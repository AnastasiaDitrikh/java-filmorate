package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> getListAllFriends(Long userId);

    List<User> findCommonFriends(Long userId, Long anotherId);

    List<User> findAll();

    User add(User user);

    User update(User user);

    User getUserById(Long userId);
}
