package ru.yandex.practicum.filmorate.service.exceptions;

/**
 * Исключение ValidationException представляет собой исключение, которое выбрасывается,
 * когда возникает ошибка валидации данных.
 */
public class ValidationException extends RuntimeException {

    /**
     * Создает новый экземпляр ValidationException с заданным сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public ValidationException(String message) {
        super(message);
    }
}