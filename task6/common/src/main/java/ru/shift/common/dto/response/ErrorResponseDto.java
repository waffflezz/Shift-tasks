package ru.shift.common.dto.response;

import ru.shift.common.dto.Body;

public record ErrorResponseDto(
        int code,
        String message
) implements Body { }
