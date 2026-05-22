package ru.shift.client.dto;

import java.time.Instant;

public record LeftUserDto(
        String username,
        Instant time
) {}
