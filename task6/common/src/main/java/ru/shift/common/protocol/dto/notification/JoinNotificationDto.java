package ru.shift.common.protocol.dto.notification;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

/**
 * Тело уведомления о подключении нового пользователя.
 *
 * @param username никнейм пользователя
 * @param time время подключения
 */
public record JoinNotificationDto(
        String username,
        Instant time
) implements Body {}
