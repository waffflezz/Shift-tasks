package ru.shift.server.handlers;

import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.dto.notification.MessageNotificationDto;
import ru.shift.common.protocol.dto.request.MessageRequestDto;
import ru.shift.common.protocol.dto.response.MessageResponseDto;
import ru.shift.common.protocol.impl.SocketNotification;
import ru.shift.common.protocol.impl.response.SuccessResponse;
import ru.shift.server.kernel.Handler;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.ServerContext;

/**
 * Обработчик отправки текстового сообщения.
 * Подтверждает получение отправителю и рассылает сообщение всем участникам чата.
 */
public class MessageHandler implements Handler<MessageRequestDto> {
    @Override
    public void handle(Request<MessageRequestDto> request, ClientSession session, ServerContext context) {
        if (!session.isAuthenticated()) {
            session.sendError(request.getId(), 403, "user not authorized");
            return;
        }

        MessageRequestDto body = request.getBody();
        var username = session.getUsername();
        var time = body.time();
        var message = body.message();

        session.send(new SuccessResponse<>(request.getId(), new MessageResponseDto()));

        var notificationDto = new MessageNotificationDto(username, time, message);
        context.broadcaster().broadcast(new SocketNotification<>(notificationDto));
    }
}
