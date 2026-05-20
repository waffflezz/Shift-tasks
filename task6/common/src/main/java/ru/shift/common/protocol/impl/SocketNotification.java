package ru.shift.common.protocol.impl;

import ru.shift.common.protocol.MessageVisitor;
import ru.shift.common.protocol.Notification;
import ru.shift.common.protocol.dto.Body;

import java.util.UUID;

public record SocketNotification<T extends Body>(String id, T body) implements Notification<T> {
    public SocketNotification(T body) {
        this(UUID.randomUUID().toString(), body);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public T getBody() {
        return body;
    }
}
