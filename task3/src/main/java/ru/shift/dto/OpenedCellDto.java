package ru.shift.dto;

/**
 * Описывает открытую клетку.
 *
 * @param x координата клетки по X
 * @param y координата клетки по Y
 * @param mined содержит ли клетка мину
 * @param adjacentMinesCount количество соседних мин
 */
public record OpenedCellDto(
        int x,
        int y,
        boolean mined,
        int adjacentMinesCount
) {}
