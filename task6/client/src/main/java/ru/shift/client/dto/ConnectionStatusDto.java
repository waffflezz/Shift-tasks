package ru.shift.client.dto;

/**
 * DTO с информацией о статусе соединения с сервером.
 *
 * @param success признак успешного подключения
 * @param message сообщение с деталями статуса
 */
public record ConnectionStatusDto(
        boolean success,
        String message
) {}
