package ru.shift.dto;

/**
 * Представляет результат игрока.
 *
 * @param playerName имя игрока
 * @param timeValue время прохождения в секундах
 */
public record ScoreDto(
        String playerName,
        int timeValue
) {}
