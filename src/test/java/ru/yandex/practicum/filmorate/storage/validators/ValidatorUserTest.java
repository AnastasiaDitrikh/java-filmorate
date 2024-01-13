package ru.yandex.practicum.filmorate.storage.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.exceptions.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorUserTest {
    protected User userTestObject;

    @BeforeEach
    public void prepareData() {
        userTestObject = User.builder()
                .id(1L)
                .email("poligraphsharikov@gmail.com")
                .login("polsha")
                .birthday(LocalDate.of(2011, 3, 28))
                .name("Полиграф")
                .build();
    }

    @Test
    public void loginValidateTest() {
        userTestObject.setLogin("Логин с пробелом");
        Exception exceptionLoginWithSpace = assertThrows(ValidationException.class,
                () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Поле логин не может быть пустым или содержать пробелы", exceptionLoginWithSpace.getMessage());
    }

    @Test
    public void nameValidateTest() {
        userTestObject.setName(null);
        ValidatorUser.validateUser(userTestObject);
        assertEquals(userTestObject.getLogin(), userTestObject.getName());

        userTestObject.setName("");
        ValidatorUser.validateUser(userTestObject);
        assertEquals(userTestObject.getLogin(), userTestObject.getName());
    }
}