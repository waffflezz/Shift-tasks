package ru.shift.common.protocol.dto.notification;

import ru.shift.common.protocol.dto.Body;

public record DisconnectNotificationDto(
        String cause
) implements Body {}
