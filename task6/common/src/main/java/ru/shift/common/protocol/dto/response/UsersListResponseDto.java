package ru.shift.common.protocol.dto.response;

import ru.shift.common.protocol.dto.Body;

import java.util.List;

/**
 * Тело ответа со списком пользователей чата.
 *
 * @param usernames список никнеймов
 */
public record UsersListResponseDto(
        List<String> usernames
) implements Body {}
