package ru.shift.common.protocol.impl.response;

import ru.shift.common.protocol.dto.response.ErrorResponseDto;
import ru.shift.common.protocol.MessageVisitor;
import ru.shift.common.protocol.Response;

public record ErrorResponse(String id, ErrorResponseDto body) implements Response<ErrorResponseDto> {
    @Override
    public String getId() {
        return id;
    }

    @Override
    public ErrorResponseDto getBody() {
        return body;
    }

    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);
    }
}
