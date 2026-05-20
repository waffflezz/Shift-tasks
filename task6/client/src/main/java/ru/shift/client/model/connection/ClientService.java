package ru.shift.client.model.connection;

import lombok.NoArgsConstructor;
import ru.shift.common.channel.Channel;
import ru.shift.common.protocol.Notification;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.dto.request.UsersListRequestDto;
import ru.shift.common.protocol.impl.SocketRequest;

import java.net.Socket;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@NoArgsConstructor
final public class ClientService {
    private ClientCore clientCore;
    private ServerListener listener;

    public CompletableFuture<Void> connect(String ip, int port) {
        return CompletableFuture.runAsync(() -> {
            try {
                Socket socket = new Socket(ip, port);
                Channel channel = new Channel(socket);
                clientCore = new ClientCore(channel);
                listener = new ServerListener(channel, clientCore);
                listener.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T extends Body> void send(Request<T> request, ResponseConsumer consumer) {
        clientCore.send(request)
                .thenAccept(consumer::accept)
                .exceptionally(throwable -> {
                    consumer.handleFailure(throwable);
                    return null;
                });
    }

    public void auth(String username, ResponseConsumer consumer) {
        Request<LoginRequestDto> request = new SocketRequest<>(new LoginRequestDto(username, Instant.now()));

        send(request, consumer);
    }

    public void getUsersList(ResponseConsumer consumer) {
        Request<UsersListRequestDto> request = new SocketRequest<>(new UsersListRequestDto());

        send(request, consumer);
    }

    public <T extends Body> void addListener(Class<T> bodyType, Consumer<Notification<T>> handler) {
        clientCore.addNotification(bodyType, handler);
    }

    public <T extends Body> void removeListener(Class<T> bodyType, Consumer<Notification<T>> handler) {
        clientCore.removeNotification(bodyType, handler);
    }
}
