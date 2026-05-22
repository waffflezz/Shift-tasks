package ru.shift.common.channel;

import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;

/**
 * Интерфейс отправки сообщений в канал связи.
 */
public interface ChannelWriter {
    /**
     * Отправляет сообщение в канал.
     *
     * @param message сообщение для отправки
     * @throws SerializeException при ошибке сериализации
     */
    void send(Message message) throws SerializeException;
}
