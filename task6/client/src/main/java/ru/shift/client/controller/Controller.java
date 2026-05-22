package ru.shift.client.controller;

/**
 * Контракт контроллера приложения.
 * Определяет основные действия, которые может инициировать пользователь.
 */
public interface Controller {
    /**
     * Запускает приложение, отображая стартовое окно.
     */
    void start();

    /**
     * Устанавливает соединение с сервером по указанному адресу и порту.
     *
     * @param ip   IP-адрес сервера
     * @param port порт сервера
     */
    void connectToServer(String ip, int port);

    /**
     * Отправляет запрос на авторизацию с указанным никнеймом.
     *
     * @param nickname никнейм пользователя
     */
    void auth(String nickname);

    /**
     * Отправляет текстовое сообщение в чат.
     *
     * @param message текст сообщения
     */
    void sendMessage(String message);
}