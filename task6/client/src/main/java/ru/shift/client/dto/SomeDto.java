package ru.shift.client.dto;

/**
 * Хранит координаты бомбы на поле.
 *
 * @param x координата бомбы по X
 * @param y координата бомбы по Y
 */
public record SomeDto(
        int x,
        int y
) {}
