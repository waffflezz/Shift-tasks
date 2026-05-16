package ru.shift.server.session;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class UserSessionRegistry {
    private final Map<String, ClientSession> sessions = new ConcurrentHashMap<>();

    public boolean exists(String username) {
        return sessions.containsKey(username);
    }

    public void add(String username, ClientSession session) {
        sessions.put(username, session);
    }

    public void remove(String username) {
        sessions.remove(username);
    }

    public Optional<ClientSession> find(String username) {
        return Optional.ofNullable(sessions.get(username));
    }

    public Collection<ClientSession> all() {
        return sessions.values();
    }
}
