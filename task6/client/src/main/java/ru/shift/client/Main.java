package ru.shift.client;

import ru.shift.client.config.ClientConfigMapper;
import ru.shift.common.config.ConfigLoader;

/**
 * Точка входа в приложение.
 */
public class Main {
    private static final String DEFAULT_CONFIG_PATH = "client.properties";

    /**
     * Создаёт компоненты приложения и запускает начальную игру.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        String configPath = args.length > 0 ? args[0] : DEFAULT_CONFIG_PATH;

        var config = ConfigLoader.load(configPath, new ClientConfigMapper());

        Client client = new Client(config);
        client.start();
    }
}
