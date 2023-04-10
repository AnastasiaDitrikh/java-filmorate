package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    protected final Map<Long, User> users = new HashMap<>();
    protected Long idUserGen = 1L;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User add(User user) {
        ValidatorUser.validateUser(user);
        checkUserLogin(user);
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
            users.put(user.getId(), user);
            log.info("Пользователь с id = {} успешно обновлен", user.getId());
        } else {
            log.warn("Пользователь с id {} не обновлен, т.к. не зарегистрирован", user.getId());
            throw new NotFoundException("Невозможно обновить данные пользователя. Такого пользователя не существует");
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("Пользователя с id = " + userId + " нет.");
        }
        return users.get(userId);
    }

    private void checkUserLogin(User user) {
        for (User value : users.values()) {
            if (user.getLogin().equals(value.getLogin())) {
                throw new ValidationException("Пользователь с таким логином зарегистрирован");
            }
        }
    }
}