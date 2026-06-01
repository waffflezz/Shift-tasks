package ru.shift.client;

import ru.shift.client.config.ClientConfigMapper;
import ru.shift.common.config.ConfigLoader;

/**
 * Точка входа в приложение.
 */
public class Main {
    private static final String PROPERTY_FILE = "client.properties";

    /**
     * Создаёт компоненты приложения и запускает начальную игру.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        var config = ConfigLoader.load(PROPERTY_FILE, new ClientConfigMapper());

        Client client = new Client(config);
        client.start();
    }
}
