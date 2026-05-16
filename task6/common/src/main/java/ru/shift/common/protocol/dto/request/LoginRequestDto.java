package ru.shift.common.protocol.dto.request;

import ru.shift.common.protocol.dto.Body;

public record LoginRequestDto(
        String username
) implements Body { }
