package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс MpaDao предоставляет методы для работы с системой рейтингового ограничения фильмов (MPA) в базе данных.
 * Извлекает все системы рейтингового ограничения фильмов из базы данных.
 * Извлекает систему рейтингового ограничения фильма по его ID из базы данных.
 * Извлекает систему рейтингового ограничения фильма по его ID из базы данных.
 */

public interface MpaDao {

    Collection<Mpa> findAllMpa();

    Optional<Mpa> getMpaById(Long mpaId);

    Optional<Mpa> getMpaByFilmId(Long id);
}