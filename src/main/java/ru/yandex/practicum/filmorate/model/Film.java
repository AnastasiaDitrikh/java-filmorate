package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Film представляет собой модель фильма.
 * Используется для хранения информации о фильме.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Size(max = 200)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Positive
    private int duration;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @Builder.Default
    private Long likes = 0L;

    @NotNull
    private Mpa mpa;

    @Builder.Default
    private List<Genre> genres = new ArrayList<>();
}