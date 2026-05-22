package ru.shift.common.protocol;

import ru.shift.common.protocol.dto.Body;

/**
 * Запрос от клиента к серверу.
 *
 * @param <T> тип тела запроса
 */
public interface Request<T extends Body> extends Message {
    T getBody();
}
