package ru.shift.client.model.contracts;

/**
 * Контракт подключения - управление соединением с сервером.
 */
public interface JoinContract {
    /**
     * Устанавливает соединение с сервером.
     *
     * @param ip IP-адрес сервера
     * @param port порт сервера
     */
    void connect(String ip, int port);

    /**
     * Отправляет запрос на авторизацию.
     *
     * @param username никнейм пользователя
     */
    void auth(String username);

    /**
     * Разрывает соединение с сервером.
     *
     * @param cause причина отключения
     */
    void disconnect(String cause);
}
