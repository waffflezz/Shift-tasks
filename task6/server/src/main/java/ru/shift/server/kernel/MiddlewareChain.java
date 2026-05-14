package ru.shift.server.kernel;

import ru.shift.common.dto.Body;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;

import java.util.List;

public class MiddlewareChain {
    private final List<Middleware> middlewares;
    private final Handler<?> handler;

    private int index = 0;

    public MiddlewareChain(
            List<Middleware> middlewares,
            Handler<?> handler
    ) {
        this.middlewares = middlewares;
        this.handler = handler;
    }

    @SuppressWarnings("unchecked")
    public Response<?> next(
            Request<?> request
    ) {

        if (index < middlewares.size()) {

            Middleware middleware = middlewares.get(index++);

            return middleware.handle(
                    request,
                    this
            );
        }

        Handler<Body> h = (Handler<Body>) handler;
        h.handle((Request<Body>) request);

        return null;
    }
}
