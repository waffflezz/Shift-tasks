package ru.shift.client.model.connection;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.channel.Channel;
import ru.shift.common.protocol.Notification;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.dto.request.MessageRequestDto;
import ru.shift.common.protocol.dto.request.UsersListRequestDto;
import ru.shift.common.protocol.impl.SocketRequest;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
@NoArgsConstructor
public final class ClientService implements AutoCloseable {
    private ClientConnection connection;

    public CompletableFuture<Void> connect(String ip, int port) {
        return CompletableFuture.runAsync(() -> {
            try {
                Socket socket = new Socket(ip, port);
                Channel channel = new Channel(socket);

                this.connection = new ClientConnection(channel);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T extends Body> void send(Request<T> request, ResponseConsumer consumer) {
        connection.send(request)
                .thenAccept(consumer::accept)
                .exceptionally(e -> {
                    consumer.handleFailure(e);
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

    public void sendMessage(String message, ResponseConsumer consumer) {
        Request<MessageRequestDto> request = new SocketRequest<>(new MessageRequestDto(message, Instant.now()));

        send(request, consumer);
    }

    public <T extends Body> void addListener(Class<T> type, Consumer<Notification<T>> handler) {
        connection.subscribe(type, handler);
    }

    public <T extends Body> void removeListener(Class<T> type, Consumer<Notification<T>> handler) {
        connection.unsubscribe(type, handler);
    }

    public void disconnect() throws IOException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            connection.close();
        }
    }
}