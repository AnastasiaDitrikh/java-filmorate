package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Класс UserController представляет собой контроллер для обработки запросов, связанных с пользователем.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    /**
     * Метод findAll возвращает список всех пользователей.
     *
     * @return список объектов User
     */
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * Метод add добавляет нового пользователя.
     *
     * @param user объект User для добавления
     * @return объект User, добавленный в базу данных
     */
    @PostMapping
    public User add(@Valid @RequestBody User user) {
        return userService.add(user);
    }

    /**
     * Метод update обновляет информацию о пользователе.
     *
     * @param user объект User для обновления
     * @return объект User, обновленный в базе данных
     */
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    /**
     * Метод getUserById возвращает пользователя по его ID.
     *
     * @param id ID пользователя для извлечения
     * @return объект User
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Метод addFriend добавляет друга пользователю.
     *
     * @param id       ID пользователя
     * @param friendId ID друга
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    /**
     * Метод deleteFriend удаляет друга пользователя.
     *
     * @param id       ID пользователя
     * @param friendId ID друга
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    /**
     * Метод getFriends возвращает список друзей пользователя по его ID.
     *
     * @param id ID пользователя
     * @return список объектов User, представляющих друзей пользователя
     */
    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Long id) {
        return userService.getFriendsByUserId(id);
    }

    /**
     * Метод findCommonFriends возвращает список общих друзей между двумя пользователями.
     *
     * @param id      ID первого пользователя
     * @param otherId ID второго пользователя
     * @return список объектов User, представляющих общих друзей
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> findCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.findCommonFriends(id, otherId);
    }
}