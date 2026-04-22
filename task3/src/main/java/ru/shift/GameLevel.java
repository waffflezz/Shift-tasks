package ru.shift;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Содержит предопределённые конфигурации игры.
 */
@Getter
@RequiredArgsConstructor
public enum GameLevel {
    NOVICE(9, 9, 10),
    MEDIUM(16, 16, 40),
    EXPERT(30, 16, 99);

    private final int width;
    private final int height;
    private final int minesCount;
}
