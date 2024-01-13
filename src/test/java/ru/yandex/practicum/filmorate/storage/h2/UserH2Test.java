package ru.yandex.practicum.filmorate.storage.h2;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserH2Test {

    private final UserH2 userH2;
    private final UserService userService;
    private User userTest;

    @BeforeEach
    public void prepareData() {
        userTest = User.builder()
                .email("poligraphsharikov@gmail.com")
                .login("polsha")
                .birthday(LocalDate.of(2011, 3, 28))
                .name("Полиграф")
                .build();
        userH2.add(userTest);
    }

    @Test
    void getUserById() {
        Optional<User> checkedUser = userH2.getUserById(1L);
        Assertions.assertThat(checkedUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("id", 1L))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("email", "poligraphsharikov@gmail.com"))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("login", "polsha"))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("birthday", LocalDate.of(2011, 3, 28)))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("name", "Полиграф"));
    }

    @Test
    void updateUser() {
        userTest.setLogin("Alex");
        userTest.setName("");
        userTest.setEmail("graphsharikov@gmail.com");
        userTest.setBirthday(LocalDate.of(2002, 3, 28));
        userService.update(userTest);
        Optional<User> checkedUser = userH2.getUserById(1L);
        Assertions.assertThat(checkedUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("id", 1L))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("name", "Alex"))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("email", "graphsharikov@gmail.com"))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("birthday", LocalDate.of(2002, 3, 28)))
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user).hasFieldOrPropertyWithValue("login", "Alex"));
    }

    @Test
    void findAll() {
        User secondUser = User.builder()
                .name("")
                .login("Alex")
                .email("graphsharikov@gmail.com")
                .birthday(LocalDate.of(2002, 7, 2))
                .build();
        userH2.add(secondUser);
        Collection<User> checkedListUsers = userH2.findAll();
        Assertions.assertThat(checkedListUsers).isNotEmpty().isNotNull().doesNotHaveDuplicates();
        Assertions.assertThat(checkedListUsers).extracting("name").contains(secondUser.getName());
        Assertions.assertThat(checkedListUsers).extracting("email").contains(userTest.getEmail());
    }
}