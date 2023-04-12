package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User add(User user) {
        return userStorage.add(user);
    }

    @Override
    public User update(User user) {
        return userStorage.update(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userStorage
                .getUserById(userId)
                .orElseThrow(()
                        -> new NotFoundException("Пользоателя с id = " + userId + "нет в базе"));
    }

    @Override
    public User addFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        log.info("Друг с id = {} успешно добален", friendId);
        return user;
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        log.info("Друг с id = {} успешно удален", friendId);
    }

    @Override
    public List<User> getListAllFriends(Long userId) {
        User user = getUserById(userId);
        List<User> listFriends = user.getFriends().stream().map(this::getUserById).collect(Collectors.toList());
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