package ru.shift.common.protocol.dto.request;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

/**
 * Тело запроса на авторизацию.
 *
 * @param username никнейм пользователя
 * @param time время запроса
 */
public record LoginRequestDto(
        String username,
        Instant time
) implements Body { }
