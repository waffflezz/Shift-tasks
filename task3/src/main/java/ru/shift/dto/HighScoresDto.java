package ru.shift.dto;

/**
 * Содержит лучшие результаты для каждого уровня сложности.
 *
 * @param noviceRecord рекорд лёгкого режима
 * @param mediumRecord рекорд среднего режима
 * @param expertRecord рекорд сложного режима
 */
public record HighScoresDto(
        ScoreDto noviceRecord,
        ScoreDto mediumRecord,
        ScoreDto expertRecord
) {}
