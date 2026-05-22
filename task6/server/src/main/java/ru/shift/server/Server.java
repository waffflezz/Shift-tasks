package ru.shift.server;

import lombok.extern.slf4j.Slf4j;
import ru.shift.common.protocol.dto.notification.DisconnectNotificationDto;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.dto.request.MessageRequestDto;
import ru.shift.common.protocol.dto.request.UsersListRequestDto;
import ru.shift.common.protocol.impl.SocketNotification;
import ru.shift.server.handlers.AuthHandler;
import ru.shift.server.handlers.MessageHandler;
import ru.shift.server.handlers.UsersListHandler;
import ru.shift.server.kernel.Dispatcher;
import ru.shift.server.kernel.SessionBroadcaster;
import ru.shift.server.listener.ClientListener;
import ru.shift.server.session.ServerContext;
import ru.shift.server.session.UserSessionRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Server {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    private final Dispatcher dispatcher;

    public Server(int port) {
        this.port = port;

        var userRegistry = new UserSessionRegistry();
        var sessionBroadcaster = new SessionBroadcaster(userRegistry);
        ServerContext context = new ServerContext(userRegistry, sessionBroadcaster);
        this.dispatcher = new Dispatcher(context);
        registryHandlers();
    }

    private void registryHandlers() {
        dispatcher
                .addHandler(LoginRequestDto.class, new AuthHandler())
                .addHandler(UsersListRequestDto.class, new UsersListHandler())
                .addHandler(MessageRequestDto.class, new MessageHandler());
    }

    public void start() {
        AtomicBoolean closed = new AtomicBoolean(false);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Start server with port: {}", port);

            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

            while (!closed.get()) {
                Socket socket = serverSocket.accept();

                pool.submit(new ClientListener(socket, dispatcher));
            }
        } catch (IOException e) {
            //TODO: log
        }
    }

    private void shutdown() {
        log.info("Server shutdown");
        dispatcher.getContext().broadcaster().broadcast(new SocketNotification<>(new DisconnectNotificationDto("Server is stop")));

        for (var session : dispatcher.getContext().users().all()) {
            try {
                session.close();
            } catch (IOException e) {
                log.warn("Error while close sessions. Error: {}", e.getMessage());
            }
        }
        pool.shutdown();
    }
}
