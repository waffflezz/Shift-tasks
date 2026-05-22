package ru.shift.client.dto;

import java.time.Instant;

/**
 * DTO с текстовым сообщением от пользователя.
 *
 * @param sender отправитель сообщения
 * @param time время отправки
 * @param message текст сообщения
 */
public record MessageDto(
        String sender,
        Instant time,
        String message
) {}
