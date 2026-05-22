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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public class ClientConnection implements AutoCloseable {
    private final Channel channel;
    private final ExecutorService reader = Executors.newSingleThreadExecutor();

    private final Map<String, CompletableFuture<Response<?>>> pending = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<Consumer<?>>> subs = new ConcurrentHashMap<>();

    public ClientConnection(Channel channel) {
        this.channel = channel;
        startReader();
    }

    private void startReader() {
        reader.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
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
                }
            } catch (IOException e) {

                // connection lost
            } catch (SerializeException e) {
                // bad message -> skip
            }
        });
    }

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

    public <T extends Body> void subscribe(Class<T> type, Consumer<Notification<T>> handler) {
        subs.computeIfAbsent(type, k -> new ArrayList<>())
                .add(handler);
    }

    public <T extends Body> void unsubscribe(Class<T> type, Consumer<Notification<T>> handler) {
        List<Consumer<?>> list = subs.get(type);
        if (list != null) list.remove(handler);
    }

    private void complete(Response<?> response) {
        CompletableFuture<Response<?>> future = pending.remove(response.getId());
        if (future != null) future.complete(response);
    }

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

    @Override
    public void close() throws IOException {
        reader.shutdown();
        channel.close();

        pending.values().forEach(f ->
                f.completeExceptionally(new IllegalStateException("Disconnected"))
        );

        pending.clear();
        subs.clear();
    }
}
