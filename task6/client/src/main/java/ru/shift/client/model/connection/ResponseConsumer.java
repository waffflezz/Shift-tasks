package ru.shift.client.model.connection;

import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

import java.util.function.Consumer;

public class ResponseConsumer {
    private Consumer<SuccessResponse<?>> successHandler;
    private Consumer<ErrorResponse> errorHandler;
    private Consumer<Throwable> failureHandler;

    public ResponseConsumer onSuccess(Consumer<SuccessResponse<?>> handler) {
        this.successHandler = handler;
        return this;
    }

    public ResponseConsumer onError(Consumer<ErrorResponse> handler) {
        this.errorHandler = handler;
        return this;
    }

    public ResponseConsumer onFailure(Consumer<Throwable> handler) {
        this.failureHandler = handler;
        return this;
    }

    public void accept(Response<?> response) {
        response.accept(new MessageVisitorAdapter() {
            @Override
            public void visit(SuccessResponse<?> successResponse) {
                if (successHandler != null) {
                    successHandler.accept(successResponse);
                }
            }

            @Override
            public void visit(ErrorResponse errorResponse) {
                if (errorHandler != null) {
                    errorHandler.accept(errorResponse);
                }
            }
        });
    }

    public void handleFailure(Throwable throwable) {
        if (failureHandler != null) {
            failureHandler.accept(throwable);
        }
    }
}
