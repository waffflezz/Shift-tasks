package ru.shift.common.protocol;

import ru.shift.common.dto.Body;

public interface Request<T extends Body> extends Message {
    T getBody();
}
