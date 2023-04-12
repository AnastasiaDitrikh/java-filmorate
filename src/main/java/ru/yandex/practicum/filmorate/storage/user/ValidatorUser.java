package ru.yandex.practicum.filmorate.storage.user;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@UtilityClass
public class ValidatorUser {

    public static void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("В качестве имени пользователя с id = {} будет использоваться логин", user.getId());
        }
        log.info("Пользователь с id = {} успешно прошел валидацию", user.getId());
    }


}
