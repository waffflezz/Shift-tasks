package ru.shift.server.kernel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.Request;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.ServerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public final class Dispatcher {
    private final Map<Class<? extends Body>, Handler<?>> handlers = new HashMap<>();

    @Getter
    private final ServerContext context;

    public <T extends Body> Dispatcher addHandler(Class<T> type, Handler<T> handler) {
        handlers.put(type, handler);
        return this;
    }

    public void dispatch(Request<?> request, ClientSession session) {
        dispatchTyped(request, session);
    }

    @SuppressWarnings("unchecked")
    private <T extends Body> void dispatchTyped(Request<?> request, ClientSession session) {
        Handler<T> handler = (Handler<T>) handlers.get(request.getBody().getClass());

        if (handler == null) {
            log.warn("Handler for dto {} not found", request.getBody().getClass());
            return;
        }

        handler.handle((Request<T>) request, session, context);
    }
}