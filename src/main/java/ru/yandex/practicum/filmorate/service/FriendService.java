package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserFriendDao;

import java.util.List;

@Service
public class FriendService {

  private final UserFriendDao friendshipDao;

  public FriendService(UserFriendDao friendshipDao) {
    this.friendshipDao = friendshipDao;
  }

  public void addFriend(Long userId, Long friendId) {
    friendshipDao.addFriend(userId, friendId);
  }

  public List<User> getFriends(Long id, Long friendId) {
    return friendshipDao.findFriends(id, friendId);
  }

  public void deleteFriend(Long userId, Long friendId) {
    friendshipDao.deleteFriend(userId, friendId);
  }

  public List<User> getFriendsByUserId(Long id) {
    return friendshipDao.getFriendsByUserId(id);
  }

  public List<User> getCommonFriends(Long userId, Long friendId) {
    return friendshipDao.getCommonFriends(userId, friendId);
  }
}
