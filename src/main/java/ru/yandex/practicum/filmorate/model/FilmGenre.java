package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс FilmGenre представляет собой связку фильмов и жанров
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmGenre {
    Long filmId;
    Long genreId;
}