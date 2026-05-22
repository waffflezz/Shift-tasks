package ru.shift.client.view.actions;

import ru.shift.client.view.views.handlers.MessageHandler;

/**
 * Действия главного окна приложения.
 */
public interface MainViewActions {
    /**
     * Возвращает действия окна подключения.
     *
     * @return действия окна подключения
     */
    JoinViewActions join();

    /**
     * Возвращает действия окна авторизации.
     *
     * @return действия окна авторизации
     */
    AuthViewAction auth();

    /**
     * Устанавливает обработчик отправки сообщения.
     *
     * @param handler обработчик, принимающий текст сообщения
     */
    void setSendMessageAction(MessageHandler handler);
}
