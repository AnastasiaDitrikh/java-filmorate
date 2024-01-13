package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс MpaController является контроллером для обработки HTTP-запросов, связанных с объектами Mpa
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;


    @GetMapping("/{id}")
    public Optional<Mpa> getMpaById(@PathVariable("id") Long mpaId) {
        return mpaService.getMpaById(mpaId);
    }

    @GetMapping()
    public Collection<Mpa> getAllMpa() {
        return mpaService.findAllMpa();
    }
}
