package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс UserFriend представляет собой связку пользователей (друзей)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFriend {
    private int userId;
    private int friendId;
}