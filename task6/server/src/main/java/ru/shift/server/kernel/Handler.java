package ru.shift.server.kernel;

import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.Request;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.ServerContext;

/**
 * Контракт обработчика запроса определённого типа.
 *
 * @param <T> тип тела запроса
 */
public interface Handler<T extends Body> {
    /**
     * Обрабатывает запрос.
     *
     * @param request запрос
     * @param session сессия клиента-отправителя
     * @param context контекст сервера
     */
    void handle(Request<T> request, ClientSession session, ServerContext context);
}
