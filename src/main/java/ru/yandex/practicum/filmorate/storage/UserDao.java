package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс UserDao предоставляет методы для работы с пользователями в базе данных.
 * Извлекает всех пользователей из базы данных.
 * Добавляет нового пользователя в базу данных.
 * Обновляет информацию о пользователе в базе данных.
 * Извлекает пользователя по его ID из базы данных.
 */

public interface UserDao {

    List<User> findAll();

    User add(User user);

    User update(User user);

    Optional<User> getUserById(Long userId);
}