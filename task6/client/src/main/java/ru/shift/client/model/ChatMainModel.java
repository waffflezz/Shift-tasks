package ru.shift.client.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.dto.AuthDto;
import ru.shift.client.dto.ConnectionStatusDto;
import ru.shift.client.dto.JoinUserDto;
import ru.shift.client.model.connection.ClientService;
import ru.shift.client.model.connection.ResponseConsumer;
import ru.shift.client.model.listeners.AuthListener;
import ru.shift.client.model.listeners.ConnectionListener;
import ru.shift.client.model.listeners.JoinUserListener;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.model.listeners.StartClientListener;
import ru.shift.client.model.listeners.UsersListListener;
import ru.shift.client.observers.ObserversRegistry;
import ru.shift.common.protocol.dto.notification.JoinNotificationDto;
import ru.shift.common.protocol.dto.response.ErrorResponseDto;
import ru.shift.common.protocol.dto.response.LoginResponseDto;
import ru.shift.common.protocol.dto.response.UsersListResponseDto;

@Slf4j
@RequiredArgsConstructor
public class ChatMainModel implements ChatModel {
    private final ObserversRegistry<ModelListener> observers;
    private final ClientService clientService;

    private void initServerNotification() {
        clientService.addListener(JoinNotificationDto.class, notification -> {
            JoinNotificationDto body = notification.getBody();
            observers.notifyListeners(JoinUserListener.class, listener -> listener.onJoin(new JoinUserDto(body.username(), body.time())));
        });
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void connect(String ip, int port) {
        clientService.connect(ip, port)
                .thenAccept(ok -> {
                    initServerNotification();
                    observers.notifyListeners(ConnectionListener.class, listener -> listener.onConnection(new ConnectionStatusDto(true, "Nice!")));
                })
                .exceptionally(e -> {
                    observers.notifyListeners(ConnectionListener.class, listener -> listener.onConnection(new ConnectionStatusDto(false, "Not nice")));
                    return null;
                });
    }

    @Override
    public void auth(String username) {
        clientService.auth(username, new ResponseConsumer()
                .onSuccess(ok -> {
                    LoginResponseDto loginResponseDto = (LoginResponseDto) ok.getBody();
                    observers.notifyListeners(AuthListener.class, listener -> listener.onAuth(new AuthDto(true, "", loginResponseDto.username())));
                    requestUsersList();
                })
                .onError(err -> {
                    ErrorResponseDto errorResponseDto = err.getBody();
                    observers.notifyListeners(AuthListener.class, listener -> listener.onAuth(new AuthDto(false, errorResponseDto.message(), "")));
                })
                .onFailure(System.out::println));
    }

    private void requestUsersList() {
        clientService.getUsersList(new ResponseConsumer()
                .onSuccess(ok -> {
                    UsersListResponseDto usersListResponseDto = (UsersListResponseDto) ok.getBody();
                    observers.notifyListeners(UsersListListener.class, listener -> listener.onUsersList(usersListResponseDto.userNicknames()));
                }));
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void start() {
        observers.notifyListeners(StartClientListener.class, StartClientListener::onStart);
    }
}
