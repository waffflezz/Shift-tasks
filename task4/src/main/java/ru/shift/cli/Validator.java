package ru.shift.cli;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Проверяет корректность аргументов командной строки.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Validator {
    /**
     * Валидирует параметры запуска и выбрасывает исключение при некорректных значениях.
     *
     * @param options параметры запуска
     */
    static void validateOptions(OptionsDto options) {
        if (options.threshold() < 0) {
            throw new IllegalArgumentException("threshold must be non-negative");
        }

        if (options.fixedThreads() != null && options.fixedThreads() <= 0) {
            throw new IllegalArgumentException("fixedThreads must be positive");
        }

        if (options.fixedTasks() != null && options.fixedTasks() <= 0) {
            throw new IllegalArgumentException("fixedTasks must be positive");
        }

        if (options.forkThreads() != null && options.forkThreads() <= 0) {
            throw new IllegalArgumentException("forkThreads must be positive");
        }

        if (options.forkThreshold() != null && options.forkThreshold() <= 0) {
            throw new IllegalArgumentException("forkThreshold must be positive");
        }
    }
}
