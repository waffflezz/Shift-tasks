package ru.shift.common.protocol.dto.response;

import ru.shift.common.protocol.dto.Body;

/**
 * Тело ответа на успешную авторизацию.
 *
 * @param username никнейм авторизованного пользователя
 */
public record LoginResponseDto(
        String username
) implements Body { }
