package ru.shift.common.protocol;

import ru.shift.common.dto.Body;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Response<T extends Body> extends Message {
    T getBody();
}
