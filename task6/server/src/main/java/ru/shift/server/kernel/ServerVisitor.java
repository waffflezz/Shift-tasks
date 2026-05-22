package ru.shift.server.kernel;

import lombok.RequiredArgsConstructor;
import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.Request;
import ru.shift.server.session.ClientSession;

/**
 * Посетитель сообщений на стороне сервера.
 * Перенаправляет входящие запросы в диспетчер.
 */
@RequiredArgsConstructor
public final class ServerVisitor extends MessageVisitorAdapter {
    private final Dispatcher dispatcher;
    private final ClientSession session;

    @Override
    public void visit(Request<?> request) {
        dispatcher.dispatch(request, session);
    }
}
