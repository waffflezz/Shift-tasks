package ru.shift.common.protocol;

import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

/**
 * Адаптер посетителя сообщений с пустыми реализациями по умолчанию.
 * Позволяет переопределять только нужные методы.
 */
public abstract class MessageVisitorAdapter implements MessageVisitor {
    @Override
    public void visit(Request<?> request) {
    }

    @Override
    public void visit(SuccessResponse<?> response) {
    }

    @Override
    public void visit(ErrorResponse response) {
    }

    @Override
    public void visit(Notification<?> notification) {
    }
}
