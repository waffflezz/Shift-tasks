package ru.shift.dto;

/**
 * Описывает изменение состояния флага у клетки.
 *
 * @param x координата клетки по X
 * @param y координата клетки по Y
 * @param flagged новое состояние флага
 * @param remainingMinesCount отображаемое количество оставшихся мин
 */
public record CellFlagChangedDto(
        int x,
        int y,
        boolean flagged,
        int remainingMinesCount
) {}
