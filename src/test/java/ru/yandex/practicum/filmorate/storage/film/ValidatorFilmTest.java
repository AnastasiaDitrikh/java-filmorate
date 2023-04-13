package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorFilmTest {
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
    public void durationReleaseDateTest() {
        filmTestObject.setReleaseDate(LocalDate.of(1895, 12, 27));
        Exception exceptionReleaseDate = assertThrows(ValidationException.class, () -> ValidatorFilm.validateFilm(filmTestObject));
        assertEquals("Некорректная дата релиза",
                exceptionReleaseDate.getMessage());
    }
}