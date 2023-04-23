package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.GenreDao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDao genreDao;


    public Collection<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    public Genre getGenreById(Long genreId) {
        return genreDao
                .getGenreById(genreId)
                .orElseThrow(()
                        -> new NotFoundException("Жанра с id = " + genreId + "нет в базе"));
    }

    public Set<Genre> findGenresByFilmId(Long id) {
        return new HashSet<>(genreDao.findGenresByFilmId(id));
    }

}
