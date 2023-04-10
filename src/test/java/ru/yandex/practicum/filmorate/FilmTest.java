package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.ValidatorFilm;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    protected Film filmTestObject;

    @BeforeEach
    public void prepareData() {
        filmTestObject = Film.builder()
                .id(1L)
                .name("самый лучший фильм")
                .description("Слишком много фильмов нравится, сложно выбрать что-то для примера")
                .releaseDate(LocalDate.of(2011, 3, 28))
                .duration(145)
                .build();
    }

    @Test
    public void nameValidateTest() {
        filmTestObject.setName("");
        Exception exceptionNullName = assertThrows(ValidationException.class, () -> ValidatorFilm.validateFilm(filmTestObject));
        assertEquals("Название фильма не может быть пустым или состоять из пробелов", exceptionNullName.getMessage());

        filmTestObject.setName("");
        Exception exceptionEmptyName = assertThrows(ValidationException.class, () -> ValidatorFilm.validateFilm(filmTestObject));
        assertEquals("Название фильма не может быть пустым или состоять из пробелов", exceptionEmptyName.getMessage());

        filmTestObject.setName("   ");
        Exception exceptionNameFromSpaces = assertThrows(ValidationException.class, () -> ValidatorFilm.validateFilm(filmTestObject));
        assertEquals("Название фильма не может быть пустым или состоять из пробелов", exceptionNameFromSpaces.getMessage());
    }

    @Test
    public void descriptionValidateTest() {
        filmTestObject.setDescription("Какое-то описание, надеюсь тут больше 200 символов))." +
                "Каждый стремится к своей заветной мечте. " +
                "Сара Голдфарб мечтала сняться в известном телешоу, ее сын Гарольд " +
                "с другом Тайроном — сказочно разбогатеть, подруга Гарольда Мэрион грезила " +
                "о собственном модном магазине, но на их пути были всевозможные препятствия. " +
                "Все они выбирают неочевидные пути достижения своих целей, и мечты по-прежнему " +
                "остаются недостижимыми, а жизни героев рушатся безвозвратно");
        Exception exceptionTooLongDescription = assertThrows(ValidationException.class, () -> ValidatorFilm.validateFilm(filmTestObject));
        assertEquals("Длина описания фильма не должна превышать 200 символов",
                exceptionTooLongDescription.getMessage());
    }

    @Test
    public void durationValidateTest() {
        filmTestObject.setDuration(-120);
        Exception exceptionNegativeDuration = assertThrows(ValidationException.class, () -> ValidatorFilm.validateFilm(filmTestObject));
        assertEquals("Продолжительность фильма не может быть отрицательной",
                exceptionNegativeDuration.getMessage());
    }

    @Test
    public void durationReleaseDateTest() {
        filmTestObject.setReleaseDate(LocalDate.of(1895, 12, 27));
        Exception exceptionReleaseDate = assertThrows(ValidationException.class, () -> ValidatorFilm.validateFilm(filmTestObject));
        assertEquals("Некорректная дата релиза",
                exceptionReleaseDate.getMessage());
    }

    @Test
    public void successfulValidateFilmTest() {
        assertDoesNotThrow(() -> ValidatorFilm.validateFilm(filmTestObject));
    }

}