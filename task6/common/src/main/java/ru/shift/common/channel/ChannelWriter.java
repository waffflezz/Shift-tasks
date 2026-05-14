package ru.shift.common.channel;

import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;

public interface ChannelWriter {
    void send(Message message) throws SerializeException;
}
