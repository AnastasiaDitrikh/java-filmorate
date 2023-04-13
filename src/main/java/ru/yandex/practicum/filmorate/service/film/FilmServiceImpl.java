package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film add(Film film) {
        return filmStorage.add(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public Film getFilmById(Long filmId) {
        return filmStorage
                .getFilmById(filmId)
                .orElseThrow(()
                        -> new NotFoundException("Фильма с id = " + filmId + "нет в базе"));
    }

    @Override
    public Film addLike(Long filmId, Long userId) {
        Film film = this.getFilmById(filmId);
        User user = userService.getUserById(userId);
        film.getLikes().add(userId);
        log.info("Like к фильму с id = {} успешно добален", filmId);
        return film;
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        if (!film.getLikes().contains(userId)) {
            log.info("Like к фильму с id = {} , от пользователя id={} не существует", filmId, userId);
            throw new NotFoundException("Like от пользователя id = " + userId + " не найден.");
        }
        film.getLikes().remove(userId);
        log.info("Like к фильму с id = {} успешно удален", film.getId());
    }

    @Override
    public List<Film> getTheMostPopularMovies(int count) {
        Collection<Film> allFilms = filmStorage.findAll();
        return allFilms.stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}



