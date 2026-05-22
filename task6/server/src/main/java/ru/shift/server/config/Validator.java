package ru.shift.server.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Проверяет корректность значений конфигурации приложения.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Validator {
    /**
     * Валидирует обязательные поля и числовые ограничения конфигурации.
     *
     * @param config конфигурация приложения
     */
    static void validate(AppConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("AppConfig не должен быть null");
        }

        if (config.port() <= 1024 || config.port() > 65535) {
            throw new IllegalArgumentException("port must be between 1024 and 65535");
        }
    }
}