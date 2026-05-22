package ru.shift.common.protocol.impl.response;

import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.MessageVisitor;
import ru.shift.common.protocol.Response;

/**
 * Сообщение об успешном выполнении запроса.
 *
 * @param <T> тип тела ответа
 * @param id идентификатор запроса
 * @param body тело с данными ответа
 */
public record SuccessResponse<T extends Body>(String id, T body) implements Response<T> {
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
