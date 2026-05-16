package ru.shift.client.model.client;

import lombok.RequiredArgsConstructor;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.impl.SocketRequest;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
final public class ClientService {
    private final ClientCore clientCore;

    public CompletableFuture<Response<?>> auth(String username) {
        Request<LoginRequestDto> request = new SocketRequest<>(new LoginRequestDto("Dima"));

        return clientCore.send(request);
    }
}
