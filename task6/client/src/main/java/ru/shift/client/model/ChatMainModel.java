package ru.shift.client.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.dto.ConnectionStatusDto;
import ru.shift.client.model.connection.ClientService;
import ru.shift.client.model.listeners.ConnectionListener;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.observers.ObserversRegistry;
import ru.shift.common.protocol.dto.response.LoginResponseDto;


@Slf4j
@RequiredArgsConstructor
public class ChatMainModel implements ChatModel {
    private final ObserversRegistry<ModelListener> observers;
    private final ClientService clientService;

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void connect(String ip, int port) {
        clientService.connect(ip, port)
                .thenAccept(ok -> {
                    observers.notifyListeners(ConnectionListener.class, listener -> listener.onConnection(new ConnectionStatusDto(true, "Nice!")));
                })
                .exceptionally(e -> {
                    observers.notifyListeners(ConnectionListener.class, listener -> listener.onConnection(new ConnectionStatusDto(false, "Not nice(")));
                    return null;
                });
    }

    @Override
    public void auth(String username) {
        clientService.auth(username)
                .thenAccept(response -> {
                    LoginResponseDto responseDto = (LoginResponseDto) response.getBody();
                })
                .exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return null;
                });
    }

    @Override
    public void disconnect() {

    }
}
