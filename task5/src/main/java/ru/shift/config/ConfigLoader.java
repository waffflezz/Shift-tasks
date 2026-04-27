package ru.shift.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigLoader {
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

    private static String normalizeConfigPath(String configFileName) {
        if (configFileName == null || configFileName.isBlank()) {
            throw new IllegalArgumentException("Имя конфиг-файла не должно быть пустым");
        }

        if (configFileName.startsWith("configs/")) {
            return configFileName;
        }

        return "configs/" + configFileName;
    }
}
