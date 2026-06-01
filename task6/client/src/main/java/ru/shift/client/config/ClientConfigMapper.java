package ru.shift.client.config;

import ru.shift.common.config.ConfigMapper;
import ru.shift.common.config.PropertyValidation;

import java.util.Properties;

/**
 * Преобразует набор properties-настроек клиента в объект {@link ClientConfig}.
 */
public class ClientConfigMapper implements ConfigMapper<ClientConfig> {
    private static final String TIMEOUT_CONNECTION_PROPERTY = "connection.timeout.ms";

    @Override
    public ClientConfig from(Properties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties must not be null");
        }

        int timeout = PropertyValidation.getRequiredInt(properties, TIMEOUT_CONNECTION_PROPERTY);

        var config = new ClientConfig(timeout);

        Validator.validateConfig(config);

        return config;
    }
}
