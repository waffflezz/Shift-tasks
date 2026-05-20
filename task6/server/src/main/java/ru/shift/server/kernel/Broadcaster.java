package ru.shift.server.kernel;

import ru.shift.common.protocol.Message;
import ru.shift.common.protocol.Notification;
import ru.shift.common.protocol.dto.Body;

public interface Broadcaster {
    void broadcast(Message message);

    void broadcastExcept(Message message, String excludeSessionId);
}
