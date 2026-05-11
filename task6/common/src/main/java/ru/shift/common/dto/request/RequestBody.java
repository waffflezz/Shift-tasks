package ru.shift.common.dto.request;

import ru.shift.common.protocol.MessageType;

public interface RequestBody {
    MessageType getMessageType();
}
