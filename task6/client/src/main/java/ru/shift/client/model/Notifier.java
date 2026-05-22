package ru.shift.client.model;

import lombok.RequiredArgsConstructor;
import ru.shift.client.dto.AuthDto;
import ru.shift.client.dto.ConnectionStatusDto;
import ru.shift.client.dto.JoinUserDto;
import ru.shift.client.dto.LeftUserDto;
import ru.shift.client.dto.MessageDto;
import ru.shift.client.model.listeners.AuthListener;
import ru.shift.client.model.listeners.ConnectionListener;
import ru.shift.client.model.listeners.DisconnectListener;
import ru.shift.client.model.listeners.JoinUserListener;
import ru.shift.client.model.listeners.LeftUserListener;
import ru.shift.client.model.listeners.MessageListener;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.model.listeners.StartClientListener;
import ru.shift.client.model.listeners.UsersListListener;
import ru.shift.client.observers.ObserversRegistry;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
final class Notifier {
    private final ObserversRegistry<ModelListener> observers;

    void notifyJoinUser(String username, Instant time) {
        var dto = new JoinUserDto(username, time);
        observers.notifyListeners(JoinUserListener.class, listener -> listener.onJoin(dto));
    }

    void notifyDisconnect() {
        observers.notifyListeners(DisconnectListener.class, DisconnectListener::onDisconnect);
    }

    void notifyLeftUser(String username, Instant time) {
        var dto = new LeftUserDto(username, time);
        observers.notifyListeners(LeftUserListener.class, listener -> listener.onLeftUser(dto));
    }

    void notifyMessage(String username, Instant time, String message) {
        var dto = new MessageDto(username, time, message);
        observers.notifyListeners(MessageListener.class, listener -> listener.onMessage(dto));
    }

    void notifyConnection(boolean success, String message) {
        var dto = new ConnectionStatusDto(success, message);
        observers.notifyListeners(ConnectionListener.class, listener -> listener.onConnection(dto));
    }

    void notifyAuth(boolean success, String errorMessage, String username) {
        var dto = new AuthDto(success, errorMessage, username);
        observers.notifyListeners(AuthListener.class, listener -> listener.onAuth(dto));
    }

    void notifyUsersList(List<String> usernames) {
        observers.notifyListeners(UsersListListener.class, listener -> listener.onUsersList(usernames));
    }

    void notifyStartClient() {
        observers.notifyListeners(StartClientListener.class, StartClientListener::onStart);
    }
}
