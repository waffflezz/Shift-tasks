package ru.shift.common.protocol.impl.response;

import java.util.function.Function;

public sealed interface Response<T> permits SuccessResponse, ErrorResponse {
    <R> R fold(
            Function<SuccessResponse<T>, R> success,
            Function<ErrorResponse<T>, R> error
    );
}
