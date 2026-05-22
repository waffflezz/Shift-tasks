package ru.shift.common.protocol.dto.notification;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

/**
 * Тело уведомления о новом сообщении в чате.
 *
 * @param username отправитель
 * @param time время отправки
 * @param message текст сообщения
 */
public record MessageNotificationDto(
        String username,
        Instant time,
        String message
) implements Body {}
