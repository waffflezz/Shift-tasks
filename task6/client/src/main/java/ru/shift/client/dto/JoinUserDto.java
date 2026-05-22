package ru.shift.client.dto;

import java.time.Instant;

/**
 * DTO с информацией о подключившемся пользователе.
 *
 * @param username никнейм пользователя
 * @param time время подключения
 */
public record JoinUserDto(
        String username,
        Instant time
) {}
