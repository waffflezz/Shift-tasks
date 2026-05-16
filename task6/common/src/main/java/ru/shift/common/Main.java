package ru.shift.common;


import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.impl.SocketRequest;

public class Main {
    public static void main(String[] args) {
        Request<LoginRequestDto> request = new SocketRequest<>(new LoginRequestDto("dima"));
        System.out.println("-----REQUEST-----");
        System.out.println(request.getId());
        System.out.println("\n");
    }
}