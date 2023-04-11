package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class FilmServiceImpl implements FilmService {

    private final FilmStorage filmStorage;

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
    public Film addLike(Long userId, Long filmId) {
        Film film = getFilmById(filmId);
        film.getLikes().add(userId);
        log.info("Like к фильму с id = {} успешно добален", filmId);
        return film;
    }

    @Override
    public void deleteLike(Long userId, Long filmId) {
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
        LikesComparator likesComparator = new LikesComparator();
        return filmStorage.findAll().stream()
                .filter(i -> count > 0)
                .sorted(likesComparator)
                .limit(count)
                .collect(Collectors.toList());
    }

    private static class LikesComparator implements Comparator<Film> {
        @Override
        public int compare(Film o1, Film o2) {
            return o1.getLikes().size() - o2.getLikes().size();
        }
    }
}
