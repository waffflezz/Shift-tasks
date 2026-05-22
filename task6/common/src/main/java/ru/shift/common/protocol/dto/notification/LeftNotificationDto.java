package ru.shift.common.protocol.dto.notification;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

/**
 * Тело уведомления об отключении пользователя.
 *
 * @param username никнейм пользователя
 * @param time время отключения
 */
public record LeftNotificationDto(
        String username,
        Instant time
) implements Body {}
