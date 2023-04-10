package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.ValidatorUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
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
    public void emailValidateTest() {
        userTestObject.setEmail(null);
        Exception exceptionNullEmail = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Электронная почта не указана", exceptionNullEmail.getMessage());


        userTestObject.setEmail("");
        Exception exceptionEmptyEmail = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Электронная почта не указана", exceptionEmptyEmail.getMessage());


        userTestObject.setEmail("   ");
        Exception exceptionEmailFromSpaces = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Электронная почта не указана", exceptionEmailFromSpaces.getMessage());

        userTestObject.setEmail("poligraphsharikovgmail.com");
        Exception exceptionEmailWithoutDog = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Электронная почта указана некорректно", exceptionEmailWithoutDog.getMessage());
    }

    @Test
    public void loginValidateTest() {
        userTestObject.setLogin(null);
        Exception exceptionNullLogin = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Поле логин не может быть пустым или содержать пробелы", exceptionNullLogin.getMessage());

        userTestObject.setLogin("");
        Exception exceptionEmptyLogin = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Поле логин не может быть пустым или содержать пробелы", exceptionEmptyLogin.getMessage());

        userTestObject.setLogin("Логин с пробелом");
        Exception exceptionLoginWithSpace = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
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

    @Test
    public void birthdayValidateTest() {
        userTestObject.setBirthday(LocalDate.of(2024, 3, 7));
        Exception exceptionBirthdayInFuture = assertThrows(ValidationException.class, () -> ValidatorUser.validateUser(userTestObject));
        assertEquals("Неккоректно введена дата рождения", exceptionBirthdayInFuture.getMessage());
    }

    @Test
    public void successfulValidateUserTest() {
        assertDoesNotThrow(() -> ValidatorUser.validateUser(userTestObject));
    }
}