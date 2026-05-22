package ru.shift.common.protocol.dto.request;

import ru.shift.common.protocol.dto.Body;

/**
 * Тело запроса списка пользователей чата.
 */
public record UsersListRequestDto(
) implements Body {}
