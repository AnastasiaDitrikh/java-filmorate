package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class ValidatorFilm {
    private static final LocalDate THE_FIRST_FILM_REALISE_DATE = LocalDate.of(1895, 12, 28);

    public static void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("В фильме с id = {} отсутствует название", film.getId());
            throw new ValidationException("Название фильма не может быть пустым или состоять из пробелов");
        } else if (film.getDescription() == null || film.getDescription().length() > 200) {
            log.warn("В фильме с id = {} превышение длины описания", film.getId());
            throw new ValidationException("Длина описания фильма не должна превышать 200 символов");
        } else if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(THE_FIRST_FILM_REALISE_DATE)) {
            log.warn("В фильме с id = {} некорректная дата релиза", film.getId());
            throw new ValidationException("Некорректная дата релиза");
        } else if (film.getDuration() <= 0) {
            log.warn("В фильме с id = {} указана некорректная продолжительность", film.getId());
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        log.info("Валидация фильма с id = {} прошла успешно", film.getId());
    }
}
