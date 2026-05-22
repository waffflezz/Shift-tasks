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

/**
 * Вспомогательный класс для уведомления наблюдателей о событиях модели.
 * Инкапсулирует создание DTO и вызов соответствующих слушателей.
 */
@RequiredArgsConstructor
final class Notifier {
    private final ObserversRegistry<ModelListener> observers;

    /**
     * Уведомляет о подключении нового пользователя к чату.
     *
     * @param username никнейм пользователя
     * @param time время подключения
     */
    void notifyJoinUser(String username, Instant time) {
        var dto = new JoinUserDto(username, time);
        observers.notifyListeners(JoinUserListener.class, listener -> listener.onJoin(dto));
    }

    /**
     * Уведомляет об отключении от сервера.
     */
    void notifyDisconnect() {
        observers.notifyListeners(DisconnectListener.class, DisconnectListener::onDisconnect);
    }

    /**
     * Уведомляет об отключении пользователя от чата.
     *
     * @param username никнейм пользователя
     * @param time время отключения
     */
    void notifyLeftUser(String username, Instant time) {
        var dto = new LeftUserDto(username, time);
        observers.notifyListeners(LeftUserListener.class, listener -> listener.onLeftUser(dto));
    }

    /**
     * Уведомляет о новом сообщении в чате.
     *
     * @param username отправитель
     * @param time время отправки
     * @param message текст сообщения
     */
    void notifyMessage(String username, Instant time, String message) {
        var dto = new MessageDto(username, time, message);
        observers.notifyListeners(MessageListener.class, listener -> listener.onMessage(dto));
    }

    /**
     * Уведомляет об изменении статуса соединения с сервером.
     *
     * @param success признак успешного подключения
     * @param message сообщение с деталями
     */
    void notifyConnection(boolean success, String message) {
        var dto = new ConnectionStatusDto(success, message);
        observers.notifyListeners(ConnectionListener.class, listener -> listener.onConnection(dto));
    }

    /**
     * Уведомляет о результате авторизации.
     *
     * @param success признак успешной авторизации
     * @param errorMessage сообщение об ошибке
     * @param username никнейм пользователя
     */
    void notifyAuth(boolean success, String errorMessage, String username) {
        var dto = new AuthDto(success, errorMessage, username);
        observers.notifyListeners(AuthListener.class, listener -> listener.onAuth(dto));
    }

    /**
     * Уведомляет о получении списка пользователей чата.
     *
     * @param usernames список никнеймов
     */
    void notifyUsersList(List<String> usernames) {
        observers.notifyListeners(UsersListListener.class, listener -> listener.onUsersList(usernames));
    }

    /**
     * Уведомляет о старте клиентского приложения.
     */
    void notifyStartClient() {
        observers.notifyListeners(StartClientListener.class, StartClientListener::onStart);
    }
}
