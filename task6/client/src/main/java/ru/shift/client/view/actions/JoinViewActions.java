package ru.shift.client.view.actions;

import ru.shift.client.view.views.handlers.ConnectionHandler;

/**
 * Действия окна подключения к серверу.
 */
public interface JoinViewActions {
    /**
     * Устанавливает обработчик нажатия на кнопку подключения.
     *
     * @param handler обработчик, принимающий IP и порт
     */
    void setConnectionAction(ConnectionHandler handler);
}
