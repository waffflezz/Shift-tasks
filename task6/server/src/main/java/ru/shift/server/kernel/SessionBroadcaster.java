package ru.shift.server.kernel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.Message;
import ru.shift.server.session.ClientSession;
import ru.shift.server.session.UserSessionRegistry;

/**
 * Реализация широковещательной рассылки сообщений.
 * Проходит по всем сессиям из реестра и отправляет сообщение активным.
 */
@Slf4j
@RequiredArgsConstructor
public class SessionBroadcaster implements Broadcaster {
    private final UserSessionRegistry users;

    @Override
    public void broadcast(Message message) {
        for (ClientSession session : users.all()) {
            if (!session.isClosed()) {
                session.send(message);
            }
        }
    }

    @Override
    public void broadcastExcept(Message message, String excludeSessionId) {
        for (ClientSession session : users.all()) {
            if (!session.isClosed() && !session.getId().equals(excludeSessionId)) {
                session.send(message);
            }
        }
    }
}
