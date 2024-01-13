package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.UserDao;
import ru.yandex.practicum.filmorate.storage.validators.ValidatorUser;

import java.util.Collection;
import java.util.List;

/**
 * Класс UserService предоставляет методы для выполнения операций пользователями.
 */
@Slf4j
@Service
public class UserService {

    private final UserDao userDao;

    private final FriendService friendService;

    public UserService(UserDao userDao, FriendService friendService) {
        this.userDao = userDao;
        this.friendService = friendService;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User add(User user) {
        ValidatorUser.validateUser(user);
        return userDao.add(user);
    }

    public User update(User user) {
        ValidatorUser.validateUser(user);
        userDao.getUserById(user.getId()).orElseThrow(()
                -> new NotFoundException("Пользоателя с id = " + user.getId() + "нет в базе"));
        return userDao.update(user);
    }

    public User getUserById(Long userId) {
        return userDao.getUserById(userId).orElseThrow(()
                -> new NotFoundException("Пользоателя с id = " + userId + "нет в базе"));
    }

    public void addFriend(Long userId, Long friendId) {
        userDao.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id='" + userId + "' not found"));
        userDao.getUserById(friendId)
                .orElseThrow(() -> new NotFoundException("User with id='" + friendId + "' not found"));

        friendService.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        friendService.deleteFriend(userId, friendId);
    }

    public Collection<User> findCommonFriends(final Long userId, final Long otherId) {
        return friendService.getCommonFriends(userId, otherId);
    }

    public Collection<User> getFriendsByUserId(final Long id) {
        userDao.getUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return friendService.getFriendsByUserId(id);
    }
}