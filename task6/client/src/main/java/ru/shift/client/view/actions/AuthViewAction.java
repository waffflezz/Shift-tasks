package ru.shift.client.view.actions;

import ru.shift.client.view.views.handlers.AuthHandler;

/**
 * Действия окна авторизации.
 */
public interface AuthViewAction {
    /**
     * Устанавливает обработчик нажатия на кнопку входа.
     *
     * @param handler обработчик, принимающий никнейм
     */
    void setAuthAction(AuthHandler handler);
}
