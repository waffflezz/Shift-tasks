package ru.shift.common.protocol;

import ru.shift.common.dto.request.RequestBody;

public interface Request<T extends RequestBody> {
    String getId();

    MessageType getMessageType();

    T getBody();
}
