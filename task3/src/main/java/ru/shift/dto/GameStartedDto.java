package ru.shift.dto;

public record GameStartedDto(
        int width,
        int height,
        int minesCount
) {}
