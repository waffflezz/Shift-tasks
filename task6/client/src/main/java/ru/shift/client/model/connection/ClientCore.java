package ru.shift.client.model.connection;

import ru.shift.common.channel.Channel;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.Response;
import ru.shift.common.protocol.dto.Body;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class ClientCore implements AutoCloseable {
    private final Channel channel;

    private final Map<String, CompletableFuture<? extends Response<?>>> pending = new ConcurrentHashMap<>();

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
            future.completeExceptionally(e);
        }

        return future;
    }

    @SuppressWarnings("unchecked")
    public void complete(Response<?> response) {
        CompletableFuture<Response<?>> future = (CompletableFuture<Response<?>>) pending.remove(response.getId());

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
