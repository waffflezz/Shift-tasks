package ru.shift.common.dto.request;

import ru.shift.common.dto.Body;

public record ChatRequestDto(
    String message
) implements Body { }
