package ru.yandex.practicum.filmorate.storage.user;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
@UtilityClass
public class ValidatorUser {

    public static void validateUser(User user) {
        String mail = user.getEmail();
        if (mail == null || mail.isBlank()) {
            log.warn("Пользователь с id = {} не указал электронную почту", user.getId());
            throw new ValidationException("Электронная почта не указана");
        } else if (!mail.contains("@")) {
            log.warn("Электронная почта для пользователя с id = {} указана некорректно", user.getId());
            throw new ValidationException("Электронная почта указана некорректно");
        } else if (user.getLogin() == null || user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.warn("Пользователь с id = {} не указал логин", user.getId());
            throw new ValidationException("Поле логин не может быть пустым или содержать пробелы");
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("В качестве имени пользователя с id = {} будет использоваться логин", user.getId());
        } else if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Пользователь с id = {} некорректно указал дату рождения", user.getId());
            throw new ValidationException("Неккоректно введена дата рождения");
        }
        log.info("Пользователь с id = {} успешно прошел валидацию", user.getId());
    }
}
