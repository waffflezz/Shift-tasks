package ru.shift.common.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Загружает конфигурацию приложения из properties-файла в ресурсах проекта.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigLoader {
    /**
     * Читает внешний properties-файл и преобразует его
     * в объект конфигурации с помощью переданного маппера.
     *
     * @param configPath путь к properties-файлу
     * @param mapper     маппер, преобразующий {@link Properties} в объект конфигурации
     * @param <T>        тип итогового объекта конфигурации
     * @return загруженная и преобразованная конфигурация
     * @throws IllegalArgumentException если путь к файлу или маппер некорректны
     * @throws IllegalStateException    если файл не найден, недоступен или произошла ошибка чтения
     */
    public static <T> T load(Path configPath, ConfigMapper<T> mapper) {
        validateConfigPath(configPath);

        Properties properties = new Properties();

        try (InputStream inputStream = Files.newInputStream(configPath)) {
            properties.load(inputStream);

            return mapper.from(properties);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config: " + configPath, e);
        }
    }

    /**
     * Читает внешний properties-файл по строковому пути.
     *
     * @param configPath путь к properties-файлу
     * @param mapper     маппер, преобразующий {@link Properties} в объект конфигурации
     * @param <T>        тип итогового объекта конфигурации
     * @return загруженная и преобразованная конфигурация
     */
    public static <T> T load(String configPath, ConfigMapper<T> mapper) {
        if (configPath == null || configPath.isBlank()) {
            throw new IllegalArgumentException("Config path must not be empty");
        }

        return load(Path.of(configPath), mapper);
    }

    /**
     * Проверяет корректность пути к конфигурационному файлу.
     *
     * @param configPath путь к конфигурационному файлу
     * @throws IllegalArgumentException если путь равен {@code null}
     * @throws IllegalStateException    если файл не существует, не является обычным файлом
     *                                  или недоступен для чтения
     */
    private static void validateConfigPath(Path configPath) {
        if (configPath == null) {
            throw new IllegalArgumentException("Config path must not be null");
        }

        if (!Files.exists(configPath)) {
            throw new IllegalStateException("Config file not found: " + configPath);
        }

        if (!Files.isRegularFile(configPath)) {
            throw new IllegalStateException("Config path is not a file: " + configPath);
        }

        if (!Files.isReadable(configPath)) {
            throw new IllegalStateException("Config file is not readable: " + configPath);
        }
    }
}
