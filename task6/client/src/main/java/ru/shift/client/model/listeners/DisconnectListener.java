package ru.shift.client.model.listeners;

/**
 * Слушатель отключения от сервера.
 */
@FunctionalInterface
public interface DisconnectListener extends ModelListener {
    /**
     * Вызывается при отключении от сервера.
     */
    void onDisconnect();
}
