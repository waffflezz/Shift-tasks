package ru.shift.server.kernel;

import ru.shift.common.dto.request.LoginRequestDto;
import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.Request;
import ru.shift.server.handlers.AuthHandler;

public final class ServerVisitor extends MessageVisitorAdapter {
    private final Dispatcher dispatcher = new Dispatcher();

    @Override
    public void visit(Request<?> request) {
        dispatcher
                .addHandler(LoginRequestDto.class, new AuthHandler())
                .dispatch(request);
    }
}
