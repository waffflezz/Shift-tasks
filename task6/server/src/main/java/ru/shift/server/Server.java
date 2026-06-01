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

/**
 * Сервер чата.
 * Принимает входящие TCP-соединения и обрабатывает их в отдельных потоках.
 */
@Slf4j
public class Server {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicBoolean closed = new AtomicBoolean(false);

    private final Dispatcher dispatcher;

    /**
     * Создаёт сервер и регистрирует обработчики запросов.
     *
     * @param port порт для прослушивания
     */
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

    /**
     * Запускает сервер.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Start server with port: {}", port);

            while (!closed.get()) {
                Socket socket = serverSocket.accept();

                pool.submit(new ClientListener(socket, dispatcher));
            }
        } catch (IOException e) {
            log.error("Error while start ServerSocket. Error: {}", e.getMessage());
        }
    }

    /**
     * shutdown-hook для корректного завершения при остановке JVM.
     */
    public void shutdown() {
        log.info("Server shutdown");
        closed.set(true);

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
