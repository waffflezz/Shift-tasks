package ru.shift.config;

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
    private static final String CONFIG_DIR = "configs/";

    /**
     * Читает файл конфигурации, преобразует его в объект настроек и валидирует результат.
     *
     * @param propertyFileName имя файла конфигурации
     * @return загруженная конфигурация
     */
    public static AppConfig load(String propertyFileName) {
        String normalizePropertyFileName = normalizeConfigPath(propertyFileName);

        Properties properties = new Properties();

        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(normalizePropertyFileName)) {
            if (inputStream == null) {
                throw new IllegalStateException("Не найден файл " + normalizePropertyFileName);
            }

            properties.load(inputStream);
            AppConfig config = new AppConfig(
                properties.getProperty("scenarioName"),
                Integer.parseInt(properties.getProperty("producerCount")),
                Integer.parseInt(properties.getProperty("consumerCount")),
                Long.parseLong(properties.getProperty("producerTime")),
                Long.parseLong(properties.getProperty("consumerTime")),
                Integer.parseInt(properties.getProperty("storageSize")),
                Long.parseLong(properties.getProperty("scenarioDurationMillis"))
            );

            Validator.validate(config);
            return config;
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить " + normalizePropertyFileName, e);
        }
    }

    /**
     * Приводит имя конфигурационного файла к пути внутри каталога ресурсов {@link ConfigLoader#CONFIG_DIR}.
     *
     * @param configFileName имя файла конфигурации
     * @return нормализованный путь к файлу конфигурации
     */
    private static String normalizeConfigPath(String configFileName) {
        if (configFileName == null || configFileName.isBlank()) {
            throw new IllegalArgumentException("Имя конфиг-файла не должно быть пустым");
        }

        if (configFileName.startsWith(CONFIG_DIR)) {
            return configFileName;
        }

        return CONFIG_DIR + configFileName;
    }
}
