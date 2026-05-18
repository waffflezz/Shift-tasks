package ru.shift.client.model.connection;

import lombok.NoArgsConstructor;
import ru.shift.common.channel.Channel;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.impl.SocketRequest;

import java.net.Socket;
import java.util.concurrent.CompletableFuture;

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
                ClientVisitor visitor = new ClientVisitor(clientCore);
                listener = new ServerListener(channel, visitor);
                listener.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Response<?>> auth(String username) {
        Request<LoginRequestDto> request = new SocketRequest<>(new LoginRequestDto("Dima"));

        return clientCore.send(request);
    }
}
