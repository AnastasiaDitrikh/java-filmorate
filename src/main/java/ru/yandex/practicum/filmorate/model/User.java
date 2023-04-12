package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
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
    @NonNull
    private LocalDate birthday;

    public final Set<Long> friends = new HashSet<>();
}
