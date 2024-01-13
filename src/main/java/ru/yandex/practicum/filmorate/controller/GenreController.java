package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

/**
 * Класс GenreController предоставляет обработчики запросов для работы с жанрами фильмов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    /**
     * Метод getGenreById возвращает жанр фильма по его ID.
     *
     * @param genreId ID жанра для извлечения
     * @return объект Genre, представляющий жанр фильма
     */
    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable("id") Long genreId) {
        return genreService.getGenreById(genreId);
    }

    /**
     * Метод getAllGenres возвращает все жанры фильмов.
     *
     * @return коллекция объектов Genre, представляющих все жанры фильмов
     */
    @GetMapping()
    public Collection<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
}