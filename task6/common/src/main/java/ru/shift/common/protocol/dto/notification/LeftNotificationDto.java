package ru.shift.common.protocol.dto.notification;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

public record LeftNotificationDto(
        String username,
        Instant time
) implements Body {}
