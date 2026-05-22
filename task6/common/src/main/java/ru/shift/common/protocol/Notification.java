package ru.shift.common.protocol;

import ru.shift.common.protocol.dto.Body;

/**
 * Уведомление - сообщение, инициируемое сервером без предварительного запроса.
 *
 * @param <T> тип тела уведомления
 */
public interface Notification<T extends Body> extends Message{
    T getBody();
}
