package ru.shift.common.channel;

import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;

import java.io.IOException;

public interface ChannelReader {
    Message read() throws IOException, SerializeException;
}
