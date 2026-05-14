package ru.shift.server;

import ru.shift.common.dto.request.LoginRequestDto;
import ru.shift.common.protocol.Message;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.impl.SocketRequest;
import ru.shift.common.serialization.JsonSerializer;
import ru.shift.server.kernel.ServerVisitor;

public class Main {
    static ServerVisitor serverVisitor = new ServerVisitor();

    public static void main(String[] args) {
        Request<LoginRequestDto> request = new SocketRequest<>(new LoginRequestDto("Dima"));
        try {
            System.out.println("-----SERIALIZE MESSAGE-----");
            String sr = JsonSerializer.serialize(request);
            System.out.println(sr);
            System.out.println("\n");

            Message message = JsonSerializer.deserialize(sr);
            message.accept(serverVisitor);
        } catch (Exception e ) {
            System.out.println(e.getCause().getMessage());
        }
    }
}