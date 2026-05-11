package ru.shift.common;


import ru.shift.common.dto.request.LoginRequestDto;
import ru.shift.common.dto.response.LoginResponseDto;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.impl.SocketRequest;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.Response;
import ru.shift.common.protocol.impl.response.SuccessResponse;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Request<LoginRequestDto> request = new SocketRequest<>(new LoginRequestDto("dima"));
        System.out.println("-----REQUEST-----");
        System.out.println(request.getId());
        System.out.println(request.getBody().getMessageType());
        System.out.println(request.getBody().username());
        System.out.println("\n");

        Response<LoginResponseDto> r = res(request);

        System.out.println("-----RESPONSE-----");
        switch (r) {
            case SuccessResponse<LoginResponseDto> ok -> {
                System.out.println("---ok---");
                System.out.println(ok.requestId());
                System.out.println(ok.body().username());
                System.out.println(ok.body().userId());
            }
            case ErrorResponse<LoginResponseDto> err -> {
                System.out.println("---err---");
                System.out.println(err.requestId());
                System.out.println(err.code());
                System.out.println(err.message());
            }
        }
        System.out.println("\n");

        r.fold(
                ok -> {
                    System.out.println(ok.body());
                    return null;
                },
                err -> {
                    System.out.println(err.message());
                    return null;
                }
        );
    }

    private static Response<LoginResponseDto> res(Request<LoginRequestDto> request) {
        Response<LoginResponseDto> response = new SuccessResponse<>(request.getId(), new LoginResponseDto("123", "Dima"));
        Response<LoginResponseDto> errorResponse = new ErrorResponse<>(request.getId(), 403, "Beda error(((");
        return errorResponse;
    }
}