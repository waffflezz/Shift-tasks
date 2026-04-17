package ru.shift.dto;

/**
 * Хранит координаты бомбы на поле.
 *
 * @param x координата бомбы по X
 * @param y координата бомбы по Y
 */
public record BombDto(
        int x,
        int y
) {}
