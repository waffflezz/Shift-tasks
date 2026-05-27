package ru.shift.server.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Properties;

/**
 * Преобразует {@link Properties} в объект конфигурации приложения с валидацией типа значения
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class AppConfigMapper {
    private static final String PORT_PROPERTY = "port";

    /**
     * Маппит {@link Properties} объект в {@link AppConfig} дто
     *
     * @param properties проперти сервера
     * @return {@link AppConfig} дто с правильными типами
     */
    static AppConfig from(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties must not be null");
        }

        int port = getRequiredInt(properties, PORT_PROPERTY);

        return new AppConfig(port);
    }

    private static int getRequiredInt(Properties properties, String propertyName) {
        String value = properties.getProperty(propertyName);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(propertyName + " property is required");
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(propertyName + " must be a number", e);
        }
    }
}
