package ru.shift.dto;

/**
 * Содержит прошедшее время, сообщённое таймером.
 *
 * @param elapsedSeconds прошедшее время в секундах
 */
public record TimerTickDto(
        int elapsedSeconds
) {}
