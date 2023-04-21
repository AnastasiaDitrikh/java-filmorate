package ru.yandex.practicum.filmorate.storage.in.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.service.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.storage.UserDao;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserDao {
  private final Map<Long, User> users = new HashMap<>();
  private Long idUserGen = 1L;

  @Override
  public List<User> findAll() {
    return new ArrayList<>(users.values());
  }

  @Override
  public User add(User user) {
    ValidatorUser.validateUser(user);
    checkUserLogin(users, user);
    user.setId(idUserGen);
    users.put(idUserGen, user);
    idUserGen++;
    log.info("Пользователь с id = {} успешно добален", user.getId());
    return user;
  }

  @Override
  public User update(User user) {
    ValidatorUser.validateUser(user);
    if (users.containsKey(user.getId())) {
      if (!users.get(user.getId()).getLogin().equals(user.getLogin())) {
        checkUserLogin(users, user);
      }
      users.put(user.getId(), user);
      log.info("Пользователь с id = {} успешно обновлен", user.getId());
    } else {
      log.warn("Пользователь с id {} не обновлен, т.к. не зарегистрирован", user.getId());
      throw new NotFoundException("Невозможно обновить данные пользователя. Такого пользователя не существует");
    }
    return user;
  }

  private void checkUserLogin(Map<Long, User> users, User user) {
    for (User value : users.values()) {
      if (user.getLogin().equals(value.getLogin())) {
        throw new ValidationException("Пользователь с таким логином зарегистрирован");
      }
    }
  }

  @Override
  public Optional<User> getUserById(Long userId) {
    return Optional.ofNullable(users.get(userId));
  }
}
