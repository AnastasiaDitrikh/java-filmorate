package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

  List<User> findAll();

  User add(User user);

  User update(User user);

  Optional<User> getUserById(Long userId);


}
