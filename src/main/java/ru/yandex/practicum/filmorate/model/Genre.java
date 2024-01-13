package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Genre представляет собой модель жанра.
 * Используется для хранения информации о жанре.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre {
    private Long id;
    private String name;
}