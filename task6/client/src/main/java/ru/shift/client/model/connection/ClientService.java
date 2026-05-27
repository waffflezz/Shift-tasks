package ru.shift.client.model.connection;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.common.channel.Channel;
import ru.shift.common.protocol.Notification;
import ru.shift.common.protocol.Request;
import ru.shift.common.protocol.dto.Body;
import ru.shift.common.protocol.dto.request.LoginRequestDto;
import ru.shift.common.protocol.dto.request.MessageRequestDto;
import ru.shift.common.protocol.dto.request.UsersListRequestDto;
import ru.shift.common.protocol.impl.SocketRequest;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.function.Consumer;

/**
 * Сервис для работы с сетевым соединением.
 * Предоставляет удобное API для подключения, отправки запросов и подписки на уведомления.
 */
@Slf4j
@NoArgsConstructor
public final class ClientService implements AutoCloseable {
    private ClientConnection connection;

    /**
     * Устанавливает соединение с сервером по указанному адресу и порту.
     *
     * @param ip IP-адрес сервера
     * @param port порт сервера
     */
    public void connect(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            Channel channel = new Channel(socket);

            this.connection = new ClientConnection(channel);
        } catch (Exception e) {
            throw new RuntimeException("Error connection. Wrong address or port", e);
        }
    }

    /**
     * Отправляет запрос и обрабатывает ответ через колбэки.
     *
     * @param request запрос
     * @param consumer обработчик ответа
     * @param <T> тип тела запроса
     */
    private <T extends Body> void send(Request<T> request, ResponseConsumer consumer) {
        connection.send(request)
                .thenAccept(consumer::accept)
                .exceptionally(e -> {
                    consumer.handleFailure(e);
                    return null;
                });
    }

    /**
     * Отправляет запрос на авторизацию.
     *
     * @param username никнейм пользователя
     * @param consumer обработчик ответа
     */
    public void auth(String username, ResponseConsumer consumer) {
        Request<LoginRequestDto> request = new SocketRequest<>(new LoginRequestDto(username, Instant.now()));

        send(request, consumer);
    }

    /**
     * Запрашивает список пользователей чата.
     *
     * @param consumer обработчик ответа
     */
    public void getUsersList(ResponseConsumer consumer) {
        Request<UsersListRequestDto> request = new SocketRequest<>(new UsersListRequestDto());

        send(request, consumer);
    }

    /**
     * Отправляет текстовое сообщение в чат.
     *
     * @param message текст сообщения
     * @param consumer обработчик ответа
     */
    public void sendMessage(String message, ResponseConsumer consumer) {
        Request<MessageRequestDto> request = new SocketRequest<>(new MessageRequestDto(message, Instant.now()));

        send(request, consumer);
    }

    /**
     * Подписывает обработчик на уведомления с указанным типом тела.
     *
     * @param type класс тела уведомления
     * @param handler обработчик уведомления
     * @param <T> тип тела уведомления
     */
    public <T extends Body> void addListener(Class<T> type, Consumer<Notification<T>> handler) {
        connection.subscribe(type, handler);
    }

    /**
     * Отписывает обработчик от уведомлений указанного типа.
     *
     * @param type класс тела уведомления
     * @param handler ранее зарегистрированный обработчик
     * @param <T> тип тела уведомления
     */
    public <T extends Body> void removeListener(Class<T> type, Consumer<Notification<T>> handler) {
        connection.unsubscribe(type, handler);
    }

    /**
     * Разрывает соединение с сервером.
     *
     * @throws IOException при ошибке закрытия соединения
     */
    public void disconnect() throws IOException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Закрывает соединение и освобождает ресурсы.
     *
     * @throws IOException при ошибке закрытия соединения
     */
    @Override
    public void close() throws IOException {
        disconnect();
    }
}