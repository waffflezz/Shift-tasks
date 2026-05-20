package ru.shift.client.view.actions;

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
