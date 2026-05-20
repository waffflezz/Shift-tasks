package ru.shift.common.protocol;

import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

public interface MessageVisitor {
    void visit(Request<?> request);

    void visit(SuccessResponse<?> response);

    void visit(ErrorResponse response);

    void visit(Notification<?> notification);
}
