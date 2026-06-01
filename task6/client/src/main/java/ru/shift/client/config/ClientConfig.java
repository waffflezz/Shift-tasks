package ru.shift.client.config;

/**
 * Конфигурация клиентского приложения.
 *
 * @param connectionTimeoutMs таймаут подключения к серверу в миллисекундах
 */
public record ClientConfig(
        int connectionTimeoutMs
) {}
