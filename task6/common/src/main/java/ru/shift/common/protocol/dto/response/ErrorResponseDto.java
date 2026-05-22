package ru.shift.common.protocol.dto.response;

import ru.shift.common.protocol.dto.Body;

/**
 * Тело ответа с ошибкой.
 *
 * @param code код ошибки
 * @param message описание ошибки
 */
public record ErrorResponseDto(
        int code,
        String message
) implements Body { }
