package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Optional;

public interface MpaDao {

  Collection<Mpa> findAllMpa();

  Optional<Mpa> getMpaById(Long mpaId);

  Optional<Mpa> getMpaByFilmId(Long id);

}
