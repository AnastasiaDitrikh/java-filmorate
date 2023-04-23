package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    @Email
    @NotEmpty
    private String email;
    @NotBlank
    private String login;

    private String name;
    @PastOrPresent
    @NotNull
    private LocalDate birthday;
}
