package ru.shift.server.kernel;

import lombok.RequiredArgsConstructor;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.Request;
import ru.shift.server.handlers.AuthHandler;
import ru.shift.server.session.ClientSession;

@RequiredArgsConstructor
public final class ServerVisitor extends MessageVisitorAdapter {
    private final Dispatcher dispatcher;
    private final ClientSession session;

    @Override
    public void visit(Request<?> request) {
        dispatcher.dispatch(request, session);
    }
}
