package ru.yandex.practicum.filmorate.service.exceptions;

/**
 * Исключение NotFoundException представляет собой исключение, которое выбрасывается, когда
 * запрашиваемый ресурс не найден.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Создает новый экземпляр NotFoundException с заданным сообщением об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public NotFoundException(String message) {
        super(message);
    }
}