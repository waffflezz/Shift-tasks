package ru.shift.cli;

public record OptionsDto(
        long threshold,
        boolean withTimeMeasurement,
        Integer fixedThreads,
        Integer fixedTasks,
        Integer forkThreads,
        Long forkThreshold
) {}
