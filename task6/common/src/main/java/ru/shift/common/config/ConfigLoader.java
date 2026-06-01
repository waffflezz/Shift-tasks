package ru.shift.common.config;

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
    /**
     * Читает properties-файл из ресурсов проекта и преобразует его
     * в объект конфигурации с помощью переданного маппера.
     *
     * @param propertyFileName имя properties-файла в ресурсах проекта
     * @param mapper маппер, преобразующий {@link Properties} в объект конфигурации
     * @param <T> тип итогового объекта конфигурации
     * @return загруженная и преобразованная конфигурация
     * @throws IllegalArgumentException если имя файла пустое или равно {@code null}
     * @throws IllegalStateException    если файл не найден или произошла ошибка чтения
     */
    public static <T> T load(String propertyFileName, ConfigMapper<T> mapper) {
        validateConfigPath(propertyFileName);

        Properties properties = new Properties();

        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (inputStream == null) {
                throw new IllegalStateException("File not found " + propertyFileName);
            }

            properties.load(inputStream);

            return mapper.from(properties);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + propertyFileName, e);
        }
    }

    /**
     * Проверяет корректность имени конфигурационного файла.
     *
     * @param configFileName имя конфигурационного файла
     * @throws IllegalArgumentException если имя файла равно {@code null} или состоит только из пробельных символов
     */
    private static void validateConfigPath(String configFileName) {
        if (configFileName == null || configFileName.isBlank()) {
            throw new IllegalArgumentException("The config file name must not be empty");
        }
    }
}
