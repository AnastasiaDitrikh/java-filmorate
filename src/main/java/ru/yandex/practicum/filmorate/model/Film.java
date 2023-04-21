package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  @JsonIgnore
  public Set<Long> likes = new HashSet<>();
  private Mpa mpa;
  @Builder.Default
  private List<Genre> genres = new ArrayList<>();
}
