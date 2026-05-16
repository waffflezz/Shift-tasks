package ru.shift.common.protocol.dto.response;

import ru.shift.common.protocol.dto.Body;

public record LoginResponseDto(
        String userId,
        String username
) implements Body { }
