package ru.shift.dto;

public record OpenedCellDto(
        int x,
        int y,
        boolean mined,
        int adjacentMinesCount
) {}
