package ru.shift.server;

import ru.shift.common.config.ConfigLoader;
import ru.shift.server.config.AppConfigMapper;

/**
 * Точка входа серверного приложения.
 */
public class Main {
    private static final String PROPERTY_FILE = "server.properties";

    public static void main(String[] args) {
        var config = ConfigLoader.load(PROPERTY_FILE, new AppConfigMapper());

        var port = config.port();

        Server server = new Server(port);

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        server.start();
    }
}