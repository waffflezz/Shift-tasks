package ru.shift.client.model.connection;

import lombok.RequiredArgsConstructor;
import ru.shift.common.channel.Channel;
import ru.shift.common.protocol.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class ServerListener implements AutoCloseable {
    private final Channel channel;
    private final ClientCore clientCore;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void start() {
        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Message message = channel.read();
                    clientCore.handleMessage(message);
                } catch (Exception e) {
                    //TODO: log
                }
            }
        });
    }

    @Override
    public void close() {
        channel.close();
        executor.shutdown();
    }
}
