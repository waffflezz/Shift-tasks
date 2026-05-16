package ru.shift.common.protocol.impl.response;

import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.MessageVisitor;
import ru.shift.common.protocol.Response;

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
