package ru.shift.common.channel;

import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;

import java.io.IOException;

/**
 * Интерфейс чтения сообщений из канала связи.
 */
public interface ChannelReader {
    /**
     * Читает следующее сообщение из канала.
     *
     * @return сообщение или null, если поток завершён
     * @throws IOException при ошибке ввода-вывода
     * @throws SerializeException при ошибке десериализации
     */
    Message read() throws IOException, SerializeException;
}
