package ru.shift.dto;

/**
 * Описывает параметры запущенной игры.
 *
 * @param width ширина поля
 * @param height высота поля
 * @param minesCount количество мин на поле
 */
public record GameStartedDto(
        int width,
        int height,
        int minesCount
) {}
