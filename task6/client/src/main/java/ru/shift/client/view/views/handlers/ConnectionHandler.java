package ru.shift.client.view.views.handlers;

/**
 * Обработчик подключения к серверу.
 */
@FunctionalInterface
public interface ConnectionHandler {
    /**
     * Вызывается при попытке подключения.
     *
     * @param ip IP-адрес сервера
     * @param port порт сервера
     */
    void handle(String ip, int port);
}
