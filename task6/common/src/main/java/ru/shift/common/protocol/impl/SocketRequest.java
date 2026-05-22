package ru.shift.common.protocol.impl;

import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.MessageVisitor;
import ru.shift.common.protocol.Request;

import java.util.UUID;

/**
 * Запрос, отправляемый клиентом на сервер.
 *
 * @param <T> тип тела запроса
 * @param id уникальный идентификатор запроса
 * @param body тело запроса
 */
public record SocketRequest<T extends Body>(String id, T body) implements Request<T> {
    /**
     * Создаёт запрос с автоматически сгенерированным идентификатором.
     *
     * @param body тело запроса
     */
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
