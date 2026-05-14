package ru.shift.server.kernel;

import ru.shift.common.dto.Body;
import ru.shift.common.protocol.Request;

public interface Handler<T extends Body> {
    void handle(Request<T> request);
}
