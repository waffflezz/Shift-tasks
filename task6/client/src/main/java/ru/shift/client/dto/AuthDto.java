package ru.shift.client.dto;

public record AuthDto(
        boolean success,
        String errorMessage,
        String username
) {}
