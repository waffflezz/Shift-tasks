package ru.shift.client.model.listeners;

import ru.shift.client.dto.MessageDto;

public interface MessageListener extends ModelListener {
    void onMessage(MessageDto messageDto);
}
