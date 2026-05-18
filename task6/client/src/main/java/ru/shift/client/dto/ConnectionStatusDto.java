package ru.shift.client.dto;

public record ConnectionStatusDto(
        boolean success,
        String message
) {}
