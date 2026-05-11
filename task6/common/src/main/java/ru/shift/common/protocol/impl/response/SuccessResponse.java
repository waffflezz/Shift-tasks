package ru.shift.common.protocol.impl.response;

import java.util.function.Function;

public record SuccessResponse<T>(String requestId, T body) implements Response<T> {
    private SuccessResponse() {
        this(null, null);
    }

    @Override
    public <R> R fold(
            Function<SuccessResponse<T>, R> success,
            Function<ErrorResponse<T>, R> error
    ) {
        return success.apply(this);
    }
}
