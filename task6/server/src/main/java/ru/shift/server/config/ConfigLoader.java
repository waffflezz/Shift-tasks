package ru.shift.server.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Загружает конфигурацию приложения из properties-файла в ресурсах проекта.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigLoader {
    private static final String PORT_PROPERTY = "port";

    /**
     * Читает файл конфигурации, преобразует его в объект настроек и валидирует результат.
     *
     * @param propertyFileName имя файла конфигурации
     * @return загруженная конфигурация
     */
    public static AppConfig load(String propertyFileName) {
        validateConfigPath(propertyFileName);

        Properties properties = new Properties();

        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (inputStream == null) {
                throw new IllegalStateException("File not found " + propertyFileName);
            }

            properties.load(inputStream);
            AppConfig config = new AppConfig(Integer.parseInt(properties.getProperty(PORT_PROPERTY)));

            Validator.validate(config);
            return config;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + propertyFileName, e);
        }
    }

    /**
     * Валидирует имя конфигурационного файла.
     *
     * @param configFileName имя файла конфигурации
     */
    private static void validateConfigPath(String configFileName) {
        if (configFileName == null || configFileName.isBlank()) {
            throw new IllegalArgumentException("The config file name must not be empty");
        }
    }
}
