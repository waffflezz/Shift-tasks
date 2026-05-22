package ru.shift.common.protocol.dto.request;

import ru.shift.common.protocol.dto.Body;

import java.time.Instant;

public record MessageRequestDto(
        String message,
        Instant time
) implements Body {}
