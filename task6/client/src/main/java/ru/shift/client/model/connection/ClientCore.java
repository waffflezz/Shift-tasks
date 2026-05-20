package ru.shift.client.model.connection;

import ru.shift.common.channel.Channel;
import ru.shift.common.protocol.Message;
import ru.shift.common.protocol.MessageVisitorAdapter;
import ru.shift.common.protocol.Notification;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.impl.response.ErrorResponse;
import ru.shift.common.protocol.impl.response.SuccessResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

final class ClientCore implements AutoCloseable {
    private final Channel channel;
    private final Map<String, CompletableFuture<Response<?>>> pending = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<Consumer<?>>> notificationHandlers = new ConcurrentHashMap<>();
    private final ExecutorService readerExecutor = Executors.newSingleThreadExecutor();

    public ClientCore(Channel channel) {
        this.channel = channel;
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

    public <T extends Body> void addNotification(Class<T> type, Consumer<Notification<T>> handler) {
        notificationHandlers
                .computeIfAbsent(type, k -> new ArrayList<>())
                .add(handler);
    }

    public <T extends Body> void removeNotification(Class<T> type, Consumer<Notification<T>> handler) {
        List<Consumer<?>> handlers = notificationHandlers.get(type);
        if (handlers != null) {
            handlers.remove(handler);
        }
    }

    public void handleMessage(Message message) {
        message.accept(new MessageVisitorAdapter() {
            @Override
            public void visit(SuccessResponse<?> response) {
                complete(response);
            }

            @Override
            public void visit(ErrorResponse response) {
                complete(response);
            }

            @Override
            public void visit(Notification<?> notification) {
                handleNotification(notification);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void handleNotification(Notification<?> notification) {
        Body body = notification.getBody();
        if (body != null) {
            List<Consumer<?>> handlers = notificationHandlers.get(body.getClass());

            if (handlers != null) {
                handlers.forEach(handler -> {
                    try {
                        ((Consumer<Notification<?>>) handler).accept(notification);
                    } catch (Exception e) {
                        //TODO: log
                    }
                });
            }
        }
    }

    private void complete(Response<?> response) {
        CompletableFuture<Response<?>> future = pending.remove(response.getId());

        if (future != null) {
            future.complete(response);
        }
    }

    @Override
    public void close() {
        try {
            channel.close();
        } catch (Exception ignored) {
        }

        readerExecutor.shutdownNow();

        pending.values().forEach(f ->
                f.completeExceptionally(
                        new IllegalStateException("Client disconnected")
                )
        );

        pending.clear();
    }
}