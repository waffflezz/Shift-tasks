package ru.shift.dto;

import java.util.List;

/**
 * Содержит все сгенерированные позиции бомб для новой игры.
 *
 * @param bombs координаты сгенерированных бомб
 */
public record BombsGeneratedDto(
        List<BombDto> bombs
) {}
