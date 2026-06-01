package ru.shift.server;

import ru.shift.common.config.ConfigLoader;
import ru.shift.server.config.AppConfigMapper;

/**
 * Точка входа серверного приложения.
 */
public class Main {
    private static final String DEFAULT_CONFIG_PATH = "server.properties";

    public static void main(String[] args) {
        String configPath = args.length > 0 ? args[0] : DEFAULT_CONFIG_PATH;

        var config = ConfigLoader.load(configPath, new AppConfigMapper());

        var port = config.port();

        Server server = new Server(port);

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        server.start();
    }
}