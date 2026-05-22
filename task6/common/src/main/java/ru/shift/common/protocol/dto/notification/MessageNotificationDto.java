package ru.shift.common.protocol.dto.notification;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

public record MessageNotificationDto(
        String username,
        Instant time,
        String message
) implements Body {}
