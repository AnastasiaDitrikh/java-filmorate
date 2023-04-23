package ru.yandex.practicum.filmorate.storage.validators;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.exceptions.ValidationException;

import java.time.LocalDate;

@Slf4j
@UtilityClass
public class ValidatorFilm {
    private static final LocalDate THE_FIRST_FILM_REALISE_DATE = LocalDate.of(1895, 12, 28);

    public void validateFilm(Film film) {

        if (film.getReleaseDate().isBefore(THE_FIRST_FILM_REALISE_DATE)) {
            log.warn("В фильме с id = {} некорректная дата релиза", film.getId());
            throw new ValidationException("Некорректная дата релиза");
        }
        log.info("Валидация фильма с id = {} прошла успешно", film.getId());

    }
}
