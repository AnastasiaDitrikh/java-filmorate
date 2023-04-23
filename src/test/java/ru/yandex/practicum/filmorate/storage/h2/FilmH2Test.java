package ru.yandex.practicum.filmorate.storage.h2;


import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FilmH2Test {


    private final FilmH2 filmH2;
    private Film testFilm;

    @BeforeEach
    public void prepareData() {
        testFilm = Film.builder()
                .name("The best movie")
                .description("Description")
                .releaseDate(LocalDate.of(2011, 3, 28))
                .duration(145)
                .mpa(new Mpa(4L, "R"))
                .build();
        filmH2.add(testFilm);
    }


    @Test
    void getFilmById() {
        Optional<Film> checkedFilm = filmH2.getFilmById(1L);
        Assertions.assertThat(checkedFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("id", 1L))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("name", "The best movie"))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("description", "Description"))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(2011, 3, 28)))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("duration", 145))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("mpa", new Mpa(4L, "R")));


    }


    @Test
    void updateFilm() {
        testFilm.setName("The best movie 2");
        testFilm.setDescription("New description");
        testFilm.setReleaseDate(LocalDate.of(2020, 3, 28));
        testFilm.setDuration(300);
        testFilm.setMpa(new Mpa(1L, "G"));
        filmH2.update(testFilm);
        Optional<Film> checkedFilm = filmH2.getFilmById(1L);
        Assertions.assertThat(checkedFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("id", 1L))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("name", "The best movie 2"))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("description", "New description"))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(2020, 3, 28)))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("duration", 300))
                .hasValueSatisfying(film ->
                        Assertions.assertThat(film).hasFieldOrPropertyWithValue("mpa", new Mpa(1L, "G")));
    }

    @Test
    void findAll() {
        Film secondFilm = Film.builder()
                .name("UnknownFilm")
                .description("I don't have fantasy.")
                .duration(155)
                .releaseDate(LocalDate.of(2018, 7, 2))
                .mpa(new Mpa(1L, "G"))
                .build();
        filmH2.add(secondFilm);
        Collection<Film> checkedListFilms = filmH2.findAll();
        Assertions.assertThat(checkedListFilms).isNotEmpty().isNotNull().doesNotHaveDuplicates();
        Assertions.assertThat(checkedListFilms).extracting("description").contains(secondFilm.getDescription());
        Assertions.assertThat(checkedListFilms).extracting("name").contains(testFilm.getName());
    }
}
