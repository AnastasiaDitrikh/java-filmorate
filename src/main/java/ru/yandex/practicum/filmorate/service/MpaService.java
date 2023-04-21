package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.MpaDao;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {
  private final MpaDao mpaDao;


  public Collection<Mpa> findAllMpa() {
    return mpaDao.findAllMpa();
  }


  public Optional<Mpa> getMpaById(Long mpaId) {
    return Optional.ofNullable(mpaDao
            .getMpaById(mpaId)
            .orElseThrow(()
                    -> new NotFoundException("Пользоателя с id = " + mpaId + "нет в базе")));
  }
}
