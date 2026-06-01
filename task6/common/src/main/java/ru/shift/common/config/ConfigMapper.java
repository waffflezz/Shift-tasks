package ru.shift.common.config;

import java.util.Properties;

/**
 * Маппер конфигурации приложения.
 * Преобразует набор строковых properties-настроек в типизированный объект конфигурации.
 *
 * @param <T> тип итогового объекта конфигурации
 */
public interface ConfigMapper<T> {
    /**
     * Преобразует {@link Properties} в объект конфигурации.
     *
     * @param properties исходные properties-настройки
     * @return типизированный объект конфигурации
     * @throws IllegalArgumentException если properties некорректны
     */
    T from(Properties properties);
}
