package ru.yandex.practicum.filmorate.controller.handler;

import lombok.Getter;

/**
 * Класс ErrorResponse представляет объект, содержащий информацию об ошибке, возвращаемую в ответ на запрос.
 * Атрибуты:
 * - error: текстовое описание ошибки
 * - description: детальное описание ошибки
 */
@Getter
public class ErrorResponse {
    private final String error;
    private final String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
}