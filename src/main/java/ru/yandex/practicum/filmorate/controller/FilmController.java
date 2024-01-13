package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;

/**
 * Контроллер FilmController предоставляет обработчики запросов для работы с фильмами.
 */
@RestController
@AllArgsConstructor
@Slf4j
@Validated
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    /**
     * Метод findAll возвращает список всех фильмов.
     *
     * @return список объектов Film
     */
    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    /**
     * Метод add добавляет новый фильм.
     *
     * @param film объект Film для добавления
     * @return объект Film, добавленный в базу данных
     */
    @PostMapping
    public Film add(@Valid @RequestBody Film film) {
        return filmService.add(film);
    }

    /**
     * Метод update обновляет информацию о фильме.
     *
     * @param film объект Film для обновления
     * @return объект Film, обновленный в базе данных
     */
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    /**
     * Метод getFilmById возвращает фильм по его ID.
     *
     * @param id ID фильма для извлечения
     * @return объект Film
     */
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }

    /**
     * Метод deleteLike удаляет "лайк" фильма от пользователя.
     *
     * @param id     ID фильма
     * @param userId ID пользователя
     */
    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }

    /**
     * Метод addLikeToFilm добавляет "лайк" к фильму от пользователя.
     *
     * @param id     ID фильма
     * @param userId ID пользователя
     */
    @PutMapping("{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    /**
     * Метод getPopular возвращает коллекцию популярных фильмов.
     *
     * @param count количество фильмов для получения (по умолчанию: 10)
     * @return коллекция объектов Film, представляющих популярные фильмы
     */
    @GetMapping("/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10") @Positive int count) {
        return filmService.getMostPopularMovies(count);
    }
}