package ru.shift.client.model.connection;

import lombok.extern.slf4j.Slf4j;
import ru.shift.common.channel.Channel;
import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;
import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.Notification;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Управляет сетевым соединением с сервером.
 * Запускает фоновый поток чтения сообщений, диспетчеризует ответы и уведомления,
 * а также отправляет запросы.
 */
@Slf4j
public class ClientConnection implements AutoCloseable {
    private final Channel channel;
    private final Map<String, CompletableFuture<Response<?>>> pending = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<Consumer<?>>> subs = new ConcurrentHashMap<>();
    private Thread reader;


    /**
     * Создаёт соединение и запускает поток чтения сообщений.
     *
     * @param channel канал связи с сервером
     */
    public ClientConnection(Channel channel) {
        this.channel = channel;
        startReader();
    }

    /**
     * Запускает фоновый поток, читающий сообщения от сервера
     * и направляющий их либо в pending-запросы, либо в подписчики уведомлений.
     */
    private void startReader() {
        reader = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Message msg = channel.read();
                        if (msg == null) break;

                        msg.accept(new MessageVisitorAdapter() {
                            @Override
                            public void visit(SuccessResponse<?> r) {
                                complete(r);
                            }

                            @Override
                            public void visit(ErrorResponse r) {
                                complete(r);
                            }

                            @Override
                            public void visit(Notification<?> n) {
                                dispatch(n);
                            }
                        });
                    } catch (SerializeException e) {
                        log.warn("Can't deserialize message. Error: {}", e.getMessage());
                    }
                }
            } catch (IOException e) {
                log.error("Error when read socket. Error: {}", e.getMessage());
            }
        });
        reader.start();
    }

    /**
     * Отправляет запрос на сервер и возвращает future для ожидания ответа.
     *
     * @param request запрос
     * @param <T> тип тела запроса
     * @return future с ответом сервера
     */
    public <T extends Body> CompletableFuture<Response<?>> send(Request<T> request) {
        CompletableFuture<Response<?>> future = new CompletableFuture<>();
        pending.put(request.getId(), future);

        try {
            channel.send(request);
        } catch (Exception e) {
            pending.remove(request.getId());
            future.completeExceptionally(e);
        }

        return future;
    }

    /**
     * Подписывает обработчик на уведомления с указанным типом тела.
     *
     * @param type класс тела уведомления
     * @param handler обработчик уведомления
     * @param <T> тип тела уведомления
     */
    public <T extends Body> void subscribe(Class<T> type, Consumer<Notification<T>> handler) {
        subs.computeIfAbsent(type, k -> new ArrayList<>())
                .add(handler);
    }

    /**
     * Отписывает обработчик от уведомлений указанного типа.
     *
     * @param type класс тела уведомления
     * @param handler ранее зарегистрированный обработчик
     * @param <T> тип тела уведомления
     */
    public <T extends Body> void unsubscribe(Class<T> type, Consumer<Notification<T>> handler) {
        List<Consumer<?>> list = subs.get(type);
        if (list != null) list.remove(handler);
    }

    /**
     * Завершает pending-запрос полученным ответом.
     *
     * @param response ответ сервера
     */
    private void complete(Response<?> response) {
        CompletableFuture<Response<?>> future = pending.remove(response.getId());
        if (future != null) future.complete(response);
    }

    /**
     * Рассылает уведомление всем подписчикам, зарегистрированным на тип его тела.
     *
     * @param notification уведомление от сервера
     */
    @SuppressWarnings("unchecked")
    private void dispatch(Notification<?> notification) {
        Body body = notification.getBody();
        if (body == null) return;

        List<Consumer<?>> list = subs.get(body.getClass());
        if (list == null) return;

        for (Consumer<?> c : list) {
            try {
                ((Consumer<Notification<?>>) c).accept(notification);
            } catch (Exception ignored) {}
        }
    }

    /**
     * Закрывает соединение: останавливает поток чтения, закрывает канал,
     * завершает все ожидающие запросы ошибкой и очищает подписки.
     *
     * @throws IOException при ошибке закрытия канала
     */
    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.interrupt();
        }
        channel.close();

        pending.values().forEach(f ->
                f.completeExceptionally(new IllegalStateException("Disconnected"))
        );

        pending.clear();
        subs.clear();
    }
}
