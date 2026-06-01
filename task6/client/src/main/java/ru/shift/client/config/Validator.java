package ru.shift.client.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Валидирует конфигурацию клиентского приложения.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Validator {
    /**
     * Проверяет корректность объекта конфигурации клиента.
     *
     * @param config конфигурация клиента
     * @throws IllegalArgumentException если конфигурация равна {@code null}
     *                                  или таймаут подключения меньше либо равен нулю
     */
    static void validateConfig(ClientConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("ClientConfig не должен быть null");
        }

        if (config.connectionTimeoutMs() <= 0) {
            throw new IllegalArgumentException("Таймаут подключения должен быть больше 0");
        }
    }
}
