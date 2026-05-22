package ru.shift.common.protocol.dto.notification;

import ru.shift.common.protocol.dto.Body;

/**
 * Тело уведомления об отключении от сервера.
 *
 * @param cause причина отключения
 */
public record DisconnectNotificationDto(
        String cause
) implements Body {}
