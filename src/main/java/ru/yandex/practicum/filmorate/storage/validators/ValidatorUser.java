package ru.yandex.practicum.filmorate.storage.validators;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.exceptions.ValidationException;

/**
 * Класс ValidatorUser представляет валидатор пользователя.
 * Валидатор проверяет различные условия для объекта класса User и выбрасывает исключение ValidationException
 * в случае возникновения ошибки.
 */
@Slf4j
@UtilityClass
public class ValidatorUser {

    /**
     * Метод validateUser(User user) выполняет валидацию объекта User.
     *
     * @param user объект User для валидации
     * @throws ValidationException возникает, если не выполняются условия валидации
     */
    public void validateUser(User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Пользователь с id = {} указал логин с пробелами", user.getId());
            throw new ValidationException("Поле логин не может быть пустым или содержать пробелы");
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("В качестве имени пользователя с id = {} будет использоваться логин", user.getId());
        }
        log.info("Пользователь с id = {} успешно прошел валидацию", user.getId());
    }
}