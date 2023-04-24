package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserFriendDao {
  void addFriend(Long userId, Long friendId);

  void deleteFriend(Long userId, Long friendId);

  List<User> getFriendsByUserId(Long id);

  List<User> getCommonFriends(Long userId, Long friendId);

  List<User> findFriends(Long userId, Long friendId);
}
