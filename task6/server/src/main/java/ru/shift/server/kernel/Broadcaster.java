package ru.shift.server.kernel;

import ru.shift.common.protocol.Message;

/**
 * Контракт рассылки сообщений подключённым клиентам.
 */
public interface Broadcaster {
    /**
     * Отправляет сообщение всем подключённым клиентам.
     *
     * @param message сообщение
     */
    void broadcast(Message message);

    /**
     * Отправляет сообщение всем, кроме указанной сессии.
     *
     * @param message сообщение
     * @param excludeSessionId идентификатор исключаемой сессии
     */
    void broadcastExcept(Message message, String excludeSessionId);
}
