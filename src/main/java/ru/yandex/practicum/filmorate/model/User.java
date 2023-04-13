package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
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

    public final Set<Long> friends = new HashSet<>();
}
