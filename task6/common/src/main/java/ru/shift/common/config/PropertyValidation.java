package ru.shift.common.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Properties;

/**
 * Утилитный класс для чтения и валидации значений из {@link Properties}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertyValidation {
    /**
     * Получает обязательное целочисленное свойство из {@link Properties}.
     *
     * @param properties набор properties-настроек
     * @param propertyName имя обязательного свойства
     * @return значение свойства, преобразованное в {@code int}
     * @throws IllegalArgumentException если свойство отсутствует,
     *                                  содержит пустое значение
     *                                  или не может быть преобразовано в число
     */
    public static int getRequiredInt(Properties properties, String propertyName) {
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
