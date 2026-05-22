package ru.shift.common.protocol.dto.request;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

/**
 * Тело запроса на отправку сообщения в чат.
 *
 * @param message текст сообщения
 * @param time время отправки
 */
public record MessageRequestDto(
        String message,
        Instant time
) implements Body {}
