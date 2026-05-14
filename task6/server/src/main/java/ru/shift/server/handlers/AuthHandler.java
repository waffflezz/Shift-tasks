package ru.shift.server.handlers;

import ru.shift.common.dto.request.LoginRequestDto;
import ru.shift.common.dto.response.LoginResponseDto;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.impl.response.SuccessResponse;
import ru.shift.server.kernel.Handler;

public class AuthHandler implements Handler<LoginRequestDto> {
    @Override
    public void handle(Request<LoginRequestDto> request) {
        Response<LoginResponseDto> response = new SuccessResponse<>(request.getId(), new LoginResponseDto("123", "Dima"));
        System.out.println(response);
        System.out.println(request.getBody().username());
    }
}
