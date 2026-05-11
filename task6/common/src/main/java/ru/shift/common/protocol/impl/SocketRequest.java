package ru.shift.common.protocol.impl;

import ru.shift.common.dto.request.RequestBody;
import ru.shift.common.protocol.MessageType;
import ru.shift.common.protocol.Request;

import java.util.UUID;

public class SocketRequest<T extends RequestBody> implements Request<T> {
    private final String id;
    private final MessageType messageType;
    private final T body;

    public SocketRequest(T body) {
        this.id = UUID.randomUUID().toString();
        this.messageType = body.getMessageType();
        this.body = body;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public T getBody() {
        return body;
    }
}
