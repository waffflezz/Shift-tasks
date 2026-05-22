package ru.shift.common.protocol;

import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

/**
 * Посетитель для диспетчеризации сообщений по их конкретному типу.
 */
public interface MessageVisitor {
    /**
     * Вызывается для входящего запроса.
     *
     * @param request запрос
     */
    void visit(Request<?> request);

    /**
     * Вызывается для успешного ответа.
     *
     * @param response ответ
     */
    void visit(SuccessResponse<?> response);

    /**
     * Вызывается для ответа с ошибкой.
     *
     * @param response ответ
     */
    void visit(ErrorResponse response);

    /**
     * Вызывается для уведомления.
     *
     * @param notification уведомление
     */
    void visit(Notification<?> notification);
}
