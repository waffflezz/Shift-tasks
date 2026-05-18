package ru.shift.client.model.listeners;

import ru.shift.client.dto.ConnectionStatusDto;

@FunctionalInterface
public interface ConnectionListener extends ModelListener {
    void onConnection(ConnectionStatusDto connectionStatusDto);
}
