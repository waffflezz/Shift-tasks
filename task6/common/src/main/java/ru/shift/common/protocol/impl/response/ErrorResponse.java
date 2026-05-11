package ru.shift.common.protocol.impl.response;

import java.util.function.Function;

public record ErrorResponse<T>(String requestId, int code, String message) implements Response<T> {
    private ErrorResponse() {
        this(null, 0, null);
    }

    @Override
    public <R> R fold(
            Function<SuccessResponse<T>, R> success,
            Function<ErrorResponse<T>, R> error
    ) {
        return error.apply(this);
    }
}
