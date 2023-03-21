package ru.yandex.practicum.filmorate.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/films")
@RestController
@Slf4j
@Data


public class FilmController {

    protected final Map<Integer, Film> films = new HashMap<>();
    protected Integer idFilmGen=1;

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film add(@RequestBody Film film) {
        validateFilm(film);
        film.setId(idFilmGen);
        films.put(film.getId(), film);
        idFilmGen+=1;
        log.info("Фильм с id = {} успешно добален", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        validateFilm(film);
        Integer idFilm = film.getId();
        if (films.containsKey(idFilm)) {
            films.put(idFilm, film);
            log.warn("Фильм с id = {} успешно обновлен", film.getId());
        } else {
            throw new ValidationException("Неозможно обновить фильм, т.к. фильма с таким нет");
        }
        return film;
    }

    public static void validateFilm(Film film) throws ValidationException{
        if (film.getName()==null || film.getName().isEmpty() || film.getName().isBlank()) {
            log.warn("В фильме с id = {} отсутствует название", film.getId());
            throw new ValidationException("Название фильма не может быть пустым или состоять из пробелов");
        } else if (film.getDescription().length() > 200) {
            log.warn("В фильме с id = {} превышение длины описания", film.getId());
            throw new ValidationException("Длина описания фильма не должна превышать 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("В фильме с id = {} некорректная дата релиза", film.getId());
            throw new ValidationException("Некорректная дата релиза");
        } else if (film.getDuration() <= 0) {
            log.warn("В фильме с id = {} указана некорректная продолжительность", film.getId());
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        log.info("Валидация фильма с id = {} прошла успешно", film.getId());
    }

}
