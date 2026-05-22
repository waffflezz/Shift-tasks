package ru.shift.client.model.listeners;

/**
 * Слушатель запуска клиентского приложения.
 */
@FunctionalInterface
public interface StartClientListener extends ModelListener {
    /**
     * Вызывается при старте приложения.
     */
    void onStart();
}
