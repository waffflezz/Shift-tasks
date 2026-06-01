package ru.shift.server.config;

import lombok.NoArgsConstructor;
import ru.shift.common.config.ConfigMapper;
import ru.shift.common.config.PropertyValidation;

import java.util.Properties;

/**
 * Преобразует {@link Properties} в объект конфигурации приложения с валидацией типа значения
 */
@NoArgsConstructor
public final class AppConfigMapper implements ConfigMapper<AppConfig> {
    private static final String PORT_PROPERTY = "port";

    @Override
    public AppConfig from(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties must not be null");
        }

        int port = PropertyValidation.getRequiredInt(properties, PORT_PROPERTY);

        var config = new AppConfig(port);

        Validator.validateConfig(config);

        return config;
    }
}
