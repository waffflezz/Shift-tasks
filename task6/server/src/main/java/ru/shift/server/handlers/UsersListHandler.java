package ru.shift.server.handlers;

import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.dto.request.UsersListRequestDto;
import ru.shift.common.protocol.dto.response.UsersListResponseDto;
import ru.shift.common.protocol.impl.response.SuccessResponse;
import ru.shift.server.kernel.Handler;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.ServerContext;

import java.util.List;

/**
 * Обработчик запроса списка пользователей чата.
 * Возвращает никнеймы всех авторизованных и не отключённых пользователей.
 */
public class UsersListHandler implements Handler<UsersListRequestDto> {
    @Override
    public void handle(Request<UsersListRequestDto> request, ClientSession session, ServerContext context) {
        if (!session.isAuthenticated()) {
            session.sendError(request.getId(), 403, "User not authorized");
            return;
        }

        List<String> usernames = context.users().all().stream()
                .filter(s -> !s.isClosed() && s.isAuthenticated())
                .map(ClientSession::getUsername)
                .toList();

        session.send(new SuccessResponse<>(request.getId(), new UsersListResponseDto(usernames)));
    }
}
