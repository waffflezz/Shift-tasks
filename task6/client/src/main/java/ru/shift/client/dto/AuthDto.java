package ru.shift.client.dto;

/**
 * DTO с результатом авторизации.
 *
 * @param success признак успешной авторизации
 * @param errorMessage сообщение об ошибке (при неудаче)
 * @param username имя пользователя (при успехе)
 */
public record AuthDto(
        boolean success,
        String errorMessage,
        String username
) {}
