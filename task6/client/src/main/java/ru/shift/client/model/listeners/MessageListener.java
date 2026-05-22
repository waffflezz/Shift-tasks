package ru.shift.client.model.listeners;

import ru.shift.client.dto.MessageDto;

/**
 * Слушатель входящих сообщений чата.
 */
@FunctionalInterface
public interface MessageListener extends ModelListener {
    /**
     * Вызывается при получении нового сообщения.
     *
     * @param messageDto DTO с сообщением
     */
    void onMessage(MessageDto messageDto);
}
