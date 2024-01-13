package ru.yandex.practicum.filmorate.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.service.exceptions.ValidationException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

/**
 * Класс ErrorHandler является обработчиком исключений для приложения.
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Метод handleNotFoundException(NotFoundException e) обрабатывает исключение NotFoundExeption.
     *
     * @param e исключение NotFoundExeption, возникшее при обработке запроса
     * @return объект ErrorResponse с сообщением об ошибке со статусом NOT_FOUND
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.warn("Данные не обнаружены. Причина ошибки {}", Arrays.toString(e.getStackTrace()));
        return new ErrorResponse("Данные не обнаружены.", e.getMessage());
    }

    /**
     * Метод handleValidationException(ValidationException e) обрабатывает исключение ValidationException.
     *
     * @param e исключение ValidationException, возникшее при обработке запроса
     * @return объект ErrorResponse с сообщением об ошибке со статусом BAD_REQUEST
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.warn("Ошибка валидации Причина ошибки {}", Arrays.toString(e.getStackTrace()));
        return new ErrorResponse("Ошибка валидации.", e.getMessage());
    }

    /**
     * Метод handleValidationCountException(ConstraintViolationException e) обрабатывает исключение ConstraintViolationException.
     *
     * @param e исключение ConstraintViolationException, возникшее при обработке запроса
     * @return объект ResponseEntity с сообщением об ошибке и статусом BAD_REQUEST
     */
    @ExceptionHandler
    public ResponseEntity<String> handleValidationCountException(final ConstraintViolationException e) {
        log.warn("Ошибка валидации count. Причина ошибки {}", Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод handleThrowableException(Throwable e) обрабатывает непредвиденное исключение Throwable.
     *
     * @param e исключение Throwable, возникшее при обработке запроса
     * @return объект ErrorResponse с сообщением об ошибке со статусом INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowableException(final Throwable e) {
        log.warn("Непредвиденная ошибка. Причина ошибки {}", Arrays.toString(e.getStackTrace()));
        return new ErrorResponse("Непредвиденная ошибка.", e.getMessage());
    }
}