package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    protected final Map<Long, Film> films = new HashMap<>();
    protected Long idFilmGen = 1L;


    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film add(Film film) {
        ValidatorFilm.validateFilm(film);
        film.setId(idFilmGen);
        films.put(film.getId(), film);
        idFilmGen += 1;
        log.info("Фильм с id = {} успешно добален", film.getId());
        return film;
    }

    @Override
    public Film update(Film film) {
        ValidatorFilm.validateFilm(film);
        Long idFilm = film.getId();
        if (films.containsKey(idFilm)) {
            films.put(idFilm, film);
            log.warn("Фильм с id = {} успешно обновлен", film.getId());
        } else {
            throw new NotFoundException("Невозможно обновить фильм, т.к. фильма с таким нет");
        }
        return film;
    }

    @Override
    public Film getFilmById(Long filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException("Фильма с id = " + filmId + " нет.");
        }
        return films.get(filmId);
    }
}
