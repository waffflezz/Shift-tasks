package ru.shift.client.view.actions;

import ru.shift.client.view.views.CellClickHandler;

import java.awt.event.ActionListener;

/**
 * Описывает операции, доступные через основное представление.
 */
public interface MainViewActions {
    JoinViewActions join();

    AuthViewAction auth();

    ChatViewAction chat();

    /**
     * Освобождает ресурсы основного представления.
     */
    void dispose();
}
