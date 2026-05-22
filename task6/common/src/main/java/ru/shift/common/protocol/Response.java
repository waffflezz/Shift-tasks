package ru.shift.common.protocol;

import ru.shift.common.protocol.dto.Body;

/**
 * Ответ сервера на запрос клиента.
 *
 * @param <T> тип тела ответа
 */
public interface Response<T extends Body> extends Message {
    T getBody();
}
