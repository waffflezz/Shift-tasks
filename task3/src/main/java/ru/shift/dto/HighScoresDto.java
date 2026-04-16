package ru.shift.dto;

public record HighScoresDto(
        ScoreDto noviceRecord,
        ScoreDto mediumRecord,
        ScoreDto expertRecord
) {}
