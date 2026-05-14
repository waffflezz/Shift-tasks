package ru.shift.server.kernel;

import ru.shift.common.dto.Body;
import ru.shift.common.protocol.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dispatcher {

    private final Map<Class<? extends Body>, Handler<?>> handlers = new HashMap<>();

    private final List<Middleware> middlewares = new ArrayList<>();

    public <T extends Body> Dispatcher addHandler(Class<T> bodyClass, Handler<T> handler) {
        handlers.put(bodyClass, handler);
        return this;
    }

    public Dispatcher use(Middleware middleware) {
        middlewares.add(middleware);
        return this;
    }

    public void dispatch(Request<?> request) {
        Class<? extends Body> bodyClass = request.getBody().getClass();
        Handler<?> handler = handlers.get(bodyClass);

        if (handler == null) {
            throw new RuntimeException("Handler not found");
        }

        MiddlewareChain chain = new MiddlewareChain(middlewares, handler);
        chain.next(request);
    }
}