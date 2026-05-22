package ru.shift.server.session;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Потокобезопасный реестр клиентских сессий.
 * Хранит сессии по никнейму пользователя.
 */
public final class UserSessionRegistry {
    private final Map<String, ClientSession> sessions = new ConcurrentHashMap<>();

    /**
     * Проверяет, существует ли пользователь с указанным никнеймом.
     *
     * @param username никнейм
     * @return true, если пользователь в реестре
     */
    public boolean exists(String username) {
        return sessions.containsKey(username);
    }

    /**
     * Добавляет сессию пользователя в реестр.
     *
     * @param username никнейм
     * @param session сессия
     */
    public void add(String username, ClientSession session) {
        sessions.put(username, session);
    }

    /**
     * Удаляет пользователя из реестра.
     *
     * @param username никнейм
     */
    public void remove(String username) {
        sessions.remove(username);
    }

    /**
     * Возвращает все активные сессии.
     *
     * @return коллекция сессий
     */
    public Collection<ClientSession> all() {
        return sessions.values();
    }
}
