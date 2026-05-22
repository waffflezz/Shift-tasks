package ru.shift.client.view.views.handlers;

/**
 * Обработчик авторизации.
 */
@FunctionalInterface
public interface AuthHandler {
    /**
     * Вызывается при попытке входа в чат.
     *
     * @param nickname никнейм пользователя
     */
    void handle(String nickname);
}
