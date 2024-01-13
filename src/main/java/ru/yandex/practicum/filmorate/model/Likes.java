package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

/**
 * Класс Likes представляет собой связку фильмов и пользователей, которые их лайкнули
 */
@Data
@Builder
public class Likes {
    private long userId;
    private long filmId;
}