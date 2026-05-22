package ru.shift.server;

import ru.shift.server.config.ConfigLoader;

/**
 * Точка входа серверного приложения.
 */
public class Main {
    private static final String PROPERTY_FILE = "server.properties";

    public static void main(String[] args) {
        var config = ConfigLoader.load(PROPERTY_FILE);
        var port = config.port();

        Server server = new Server(port);
        server.start();
    }
}