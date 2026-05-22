package ru.shift.client.model.listeners;

import ru.shift.client.dto.ConnectionStatusDto;

/**
 * Слушатель изменения статуса соединения с сервером.
 */
@FunctionalInterface
public interface ConnectionListener extends ModelListener {
    /**
     * Вызывается при изменении статуса соединения.
     *
     * @param connectionStatusDto DTO со статусом
     */
    void onConnection(ConnectionStatusDto connectionStatusDto);
}
