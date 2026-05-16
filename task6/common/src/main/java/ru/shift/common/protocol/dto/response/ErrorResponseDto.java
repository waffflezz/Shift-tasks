package ru.shift.common.protocol.dto.response;

import ru.shift.common.protocol.dto.Body;

public record ErrorResponseDto(
        int code,
        String message
) implements Body { }
