package ru.shift.common.protocol.impl;

import ru.shift.common.dto.Body;
import ru.shift.common.protocol.MessageVisitor;
import ru.shift.common.protocol.Request;

import java.util.UUID;

public record SocketRequest<T extends Body>(String id, T body) implements Request<T> {
    public SocketRequest(T body) {
        this(UUID.randomUUID().toString(), body);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public T getBody() {
        return body;
    }

    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}
