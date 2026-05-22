package ru.shift.server.session;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.channel.Channel;
import ru.shift.common.channel.ChannelReader;
import ru.shift.common.exceptions.SerializeException;
import ru.shift.common.protocol.Message;
import ru.shift.common.protocol.dto.notification.LeftNotificationDto;
import ru.shift.common.protocol.dto.response.ErrorResponseDto;
import ru.shift.common.protocol.impl.SocketNotification;
import ru.shift.common.protocol.impl.response.ErrorResponse;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;

@Slf4j
public final class ClientSession implements AutoCloseable {
    @Getter
    private final String id;

    private final Channel channel;

    @Setter
    private ServerContext context;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private boolean authenticated;

    @Getter
    @Setter
    private boolean closed;

    public ClientSession(Socket socket) throws IOException {
        this.channel = new Channel(socket);
        this.id = channel.getClientId();
    }

    public void send(Message message) {
        try {
            channel.send(message);
        } catch (SerializeException e) {
            sendError(message.getId(), 400, "Error with serialization");
        }
    }

    public void sendError(String id, int code, String errorMessage) {
        var response = new ErrorResponse(id, new ErrorResponseDto(code, errorMessage));
        try {
            channel.send(response);
        } catch (SerializeException e) {
            //TODO: log
        }
    }

    public ChannelReader getReader() {
        return channel;
    }

    @Override
    public void close() throws IOException {
        if (closed) return;
        closed = true;

        if (!channel.isConnected()) {
            log.debug("Socket already closed");
            return;
        }

        if (context != null && isAuthenticated()) {
            log.debug("Closing ClientSession. User: {}", getUsername());
            context.broadcaster().broadcastExcept(new SocketNotification<>(new LeftNotificationDto(getUsername(), Instant.now())), getId());
        }

        log.info("Closing socket channel");
        channel.close();
    }
}
