package ru.shift.common.protocol.impl.response;

import ru.shift.common.protocol.dto.response.ErrorResponseDto;
import ru.shift.common.protocol.MessageVisitor;
import ru.shift.common.protocol.Response;

/**
 * Сообщение об ошибке, отправляемое сервером в ответ на запрос.
 *
 * @param id идентификатор запроса, на который даётся ответ
 * @param body тело с информацией об ошибке
 */
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
