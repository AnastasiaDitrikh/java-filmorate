package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс FilmorateApplication представляет собой точку входа в приложение Filmorate.
 * Запускает приложение Filmorate с использованием Spring Boot.
 */
@SpringBootApplication
public class FilmorateApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmorateApplication.class, args);
    }
}