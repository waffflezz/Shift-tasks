package ru.shift.client.dto;

import java.time.Instant;

/**
 * DTO с информацией об отключившемся пользователе.
 *
 * @param username никнейм пользователя
 * @param time время отключения
 */
public record LeftUserDto(
        String username,
        Instant time
) {}
