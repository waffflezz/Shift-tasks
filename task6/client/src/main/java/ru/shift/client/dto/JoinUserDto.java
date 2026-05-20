package ru.shift.client.dto;

import java.time.Instant;

public record JoinUserDto(
        String username,
        Instant time
) {}
