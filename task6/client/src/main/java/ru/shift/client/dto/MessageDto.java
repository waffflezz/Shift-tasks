package ru.shift.client.dto;

import java.time.Instant;

public record MessageDto(
        String sender,
        Instant time,
        String message
) {}
