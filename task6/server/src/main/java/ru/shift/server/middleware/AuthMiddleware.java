package ru.shift.server.middleware;

import ru.shift.common.dto.Body;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.server.kernel.Middleware;
import ru.shift.server.kernel.MiddlewareChain;

public class AuthMiddleware implements Middleware {
    @Override
    public Response<? extends Body> handle(Request<? extends Body> request, MiddlewareChain chain) {
        System.out.println(request.getBody().getClass());
        return chain.next(request);
    }
}
