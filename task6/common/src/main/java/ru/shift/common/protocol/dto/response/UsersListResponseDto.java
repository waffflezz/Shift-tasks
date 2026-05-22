package ru.shift.common.protocol.dto.response;

import ru.shift.common.protocol.dto.Body;

import java.util.List;

public record UsersListResponseDto(
        List<String> usernames
) implements Body {}
