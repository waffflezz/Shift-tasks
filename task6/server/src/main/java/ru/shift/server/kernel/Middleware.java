package ru.shift.server.kernel;

import ru.shift.common.dto.Body;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;

public interface Middleware {
    Response<? extends Body> handle(
            Request<? extends Body> request,
            MiddlewareChain chain
    );
}
