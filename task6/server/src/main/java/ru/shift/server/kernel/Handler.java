package ru.shift.server.kernel;

import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.Request;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.ServerContext;

public interface Handler<T extends Body> {
    void handle(Request<T> request, ClientSession session, ServerContext context);
}
