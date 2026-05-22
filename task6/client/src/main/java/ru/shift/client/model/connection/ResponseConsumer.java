package ru.shift.client.model.connection;

import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

import java.util.function.Consumer;

/**
 * Строитель колбэков для обработки ответов сервера.
 * Позволяет декларативно описать реакцию на успешный ответ, ошибку или сбой соединения.
 *
 * <pre>{@code
 * new ResponseConsumer()
 *     .onSuccess(response -> { ... })
 *     .onError(error -> { ... })
 *     .onFailure(throwable -> { ... });
 * }</pre>
 */
public class ResponseConsumer {
    private Consumer<SuccessResponse<?>> successHandler;
    private Consumer<ErrorResponse> errorHandler;
    private Consumer<Throwable> failureHandler;

    /**
     * Устанавливает обработчик успешного ответа.
     *
     * @param handler обработчик
     * @return this
     */
    public ResponseConsumer onSuccess(Consumer<SuccessResponse<?>> handler) {
        this.successHandler = handler;
        return this;
    }

    /**
     * Устанавливает обработчик ошибки сервера.
     *
     * @param handler обработчик
     * @return this
     */
    public ResponseConsumer onError(Consumer<ErrorResponse> handler) {
        this.errorHandler = handler;
        return this;
    }

    /**
     * Устанавливает обработчик сетевого сбоя.
     *
     * @param handler обработчик
     * @return this
     */
    public ResponseConsumer onFailure(Consumer<Throwable> handler) {
        this.failureHandler = handler;
        return this;
    }

    /**
     * Принимает ответ и направляет его в соответствующий обработчик.
     *
     * @param response ответ сервера
     */
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

    /**
     * Обрабатывает сбой соединения.
     *
     * @param throwable исключение
     */
    public void handleFailure(Throwable throwable) {
        if (failureHandler != null) {
            failureHandler.accept(throwable);
        }
    }
}
