package ru.shift.client.model.connection;

import lombok.RequiredArgsConstructor;
import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

@RequiredArgsConstructor
final class ClientVisitor extends MessageVisitorAdapter {
    private final ClientCore clientCore;

    @Override
    public void visit(SuccessResponse<?> response) {
        clientCore.complete(response);
    }

    @Override
    public void visit(ErrorResponse response) {
        clientCore.complete(response);
    }
}
