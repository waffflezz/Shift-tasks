package ru.shift.server.kernel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.Request;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.ServerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Диспетчер запросов.
 * Находит подходящий обработчик по типу тела запроса и делегирует ему выполнение.
 */
@Slf4j
@RequiredArgsConstructor
public final class Dispatcher {
    private final Map<Class<? extends Body>, Handler<?>> handlers = new HashMap<>();

    @Getter
    private final ServerContext context;

    /**
     * Регистрирует обработчик для указанного типа тела запроса.
     *
     * @param type класс тела запроса
     * @param handler обработчик
     * @param <T> тип тела
     * @return this
     */
    public <T extends Body> Dispatcher addHandler(Class<T> type, Handler<T> handler) {
        handlers.put(type, handler);
        return this;
    }

    /**
     * Диспетчеризует запрос, находя подходящий обработчик.
     *
     * @param request запрос
     * @param session сессия клиента-отправителя
     */
    public void dispatch(Request<?> request, ClientSession session) {
        dispatchTyped(request, session);
    }

    /**
     * Находит и вызывает обработчик для конкретного типа тела запроса.
     *
     * @param request запрос
     * @param session сессия клиента
     * @param <T> тип тела запроса
     */
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