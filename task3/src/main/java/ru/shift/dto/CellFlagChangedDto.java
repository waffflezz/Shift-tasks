package ru.shift.dto;

public record CellFlagChangedDto(
        int x,
        int y,
        boolean flagged,
        int remainingMinesCount
) {}
