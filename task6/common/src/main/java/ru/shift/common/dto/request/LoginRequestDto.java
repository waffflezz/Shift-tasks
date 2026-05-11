package ru.shift.common.dto.request;

import ru.shift.common.protocol.MessageType;

import java.time.Instant;

public record LoginRequestDto(
        String username
) implements RequestBody {
    @Override
    public MessageType getMessageType() {
        return MessageType.AUTH;
    }
}
