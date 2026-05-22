package ru.shift.client.view.actions;

import ru.shift.client.view.views.handlers.MessageHandler;

/**
 * Описывает операции, доступные через основное представление.
 */
public interface MainViewActions {
    JoinViewActions join();

    AuthViewAction auth();

    void setSendMessageAction(MessageHandler handler);

}
