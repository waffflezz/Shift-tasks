package ru.shift.server.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Проверяет корректность значений конфигурации приложения.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Validator {
    private static final int LOWER_BOUND_PORT = 1024;
    private static final int UPPER_BOUND_PORT = 65535;

    /**
     * Валидирует обязательные поля и числовые ограничения конфигурации.
     *
     * @param config конфигурация приложения
     */
    static void validateConfig(AppConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("AppConfig не должен быть null");
        }

        if (config.port() <= LOWER_BOUND_PORT || config.port() > UPPER_BOUND_PORT) {
            throw new IllegalArgumentException("port must be between " + LOWER_BOUND_PORT + " and " + UPPER_BOUND_PORT);
        }
    }
}