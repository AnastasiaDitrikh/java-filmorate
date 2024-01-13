package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс Mpa представляет собой модель рейтинга.
 * Используется для хранения информации о рейтинге.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mpa {
    private Long id;
    private String name;
}
