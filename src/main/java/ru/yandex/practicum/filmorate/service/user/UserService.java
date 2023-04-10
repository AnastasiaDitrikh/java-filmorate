package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User addFriend(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        userStorage.update(user);
        userStorage.update(friend);
        log.info("Друг с id = {} успешно добален", friendId);
        return user;
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        userStorage.update(user);
        userStorage.update(friend);
        log.info("Друг с id = {} успешно удален", friendId);
    }

    @Override
    public List<User> getListAllFriends(Long userId) {
        User user = userStorage.getUserById(userId);
        List<User> listFriends = new ArrayList<>();
        for (Long id : user.getFriends()) {
            listFriends.add(userStorage.getUserById(id));
        }
        log.info("Список друзей получен");
        return listFriends;
    }

    @Override
    public List<User> findCommonFriends(Long userId, Long anotherId) {
        List<User> commonFriends = new ArrayList<>(getListAllFriends(userId));
        commonFriends.retainAll(getListAllFriends(anotherId));
        return commonFriends;
    }
}
