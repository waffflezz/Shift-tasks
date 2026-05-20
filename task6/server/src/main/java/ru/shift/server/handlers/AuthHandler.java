package ru.shift.server.handlers;

import ru.shift.common.protocol.dto.notification.JoinNotificationDto;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.dto.response.LoginResponseDto;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.impl.SocketNotification;
import ru.shift.common.protocol.impl.response.SuccessResponse;
import ru.shift.server.kernel.Handler;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.ServerContext;

import java.time.Instant;


public class AuthHandler implements Handler<LoginRequestDto> {
    @Override
    public void handle(Request<LoginRequestDto> request, ClientSession session, ServerContext context) {
        String username = request.getBody().username();
        Instant time = request.getBody().time();

        if (context.users().exists(username)) {
            session.sendError(
                    request.getId(),
                    400,
                    "Username already taken"
            );
            return;
        }

        session.setUsername(username);
        session.setAuthenticated(true);

        context.users().add(username, session);

        context.broadcaster().broadcast(new SocketNotification<>(new JoinNotificationDto(username, time)));
        session.send(
                new SuccessResponse<>(
                        request.getId(),
                        new LoginResponseDto("123", username)
                )
        );
    }
}
