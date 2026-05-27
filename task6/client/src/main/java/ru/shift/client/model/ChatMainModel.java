package ru.shift.client.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.client.model.connection.ClientService;
import ru.shift.client.model.connection.ResponseConsumer;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.observers.ObserversRegistry;
import ru.shift.common.protocol.dto.notification.DisconnectNotificationDto;
import ru.shift.common.protocol.dto.notification.JoinNotificationDto;
import ru.shift.common.protocol.dto.notification.LeftNotificationDto;
import ru.shift.common.protocol.dto.notification.MessageNotificationDto;
import ru.shift.common.protocol.dto.response.ErrorResponseDto;
import ru.shift.common.protocol.dto.response.LoginResponseDto;
import ru.shift.common.protocol.dto.response.UsersListResponseDto;

import java.io.IOException;

/**
 * Главная модель приложения, реализующая логику чата.
 * Управляет сетевым взаимодействием через {@link ClientService}
 * и уведомляет наблюдателей о происходящих событиях.
 */
@Slf4j
public class ChatMainModel implements ChatModel {
    private final ClientService clientService;
    private final Notifier notifier;

    /**
     * Создаёт модель чата.
     *
     * @param observers реестр наблюдателей для рассылки событий
     * @param clientService сервис сетевого взаимодействия
     */
    public ChatMainModel(ObserversRegistry<ModelListener> observers, ClientService clientService) {
        this.clientService = clientService;
        this.notifier = new Notifier(observers);
    }

    /**
     * Подписывается на серверные уведомления о событиях в чате.
     */
    private void initServerNotification() {
        clientService.addListener(JoinNotificationDto.class, notification -> {
            JoinNotificationDto body = notification.getBody();
            notifier.notifyJoinUser(body.username(), body.time());
        });

        clientService.addListener(DisconnectNotificationDto.class, notification -> {
            DisconnectNotificationDto body = notification.getBody();
            disconnect(body.cause());
            notifier.notifyDisconnect();
        });

        clientService.addListener(LeftNotificationDto.class, notification -> {
            LeftNotificationDto body = notification.getBody();
            notifier.notifyLeftUser(body.username(), body.time());
        });

        clientService.addListener(MessageNotificationDto.class, notification -> {
            MessageNotificationDto body = notification.getBody();
            notifier.notifyMessage(body.username(), body.time(), body.message());
        });
    }

    @Override
    public void connect(String ip, int port) {
        try {
            clientService.connect(ip, port);
            initServerNotification();
            notifier.notifyConnection(true, "Success connection");
        } catch (Exception e) {
            notifier.notifyConnection(false, e.getMessage());
        }
    }

    @Override
    public void auth(String username) {
        clientService.auth(username, new ResponseConsumer()
                .onSuccess(ok -> {
                    LoginResponseDto loginResponseDto = (LoginResponseDto) ok.getBody();
                    notifier.notifyAuth(true, "", loginResponseDto.username());
                    requestUsersList();
                })
                .onError(err -> {
                    ErrorResponseDto errorResponseDto = err.getBody();
                    notifier.notifyAuth(false, errorResponseDto.message(), "");
                })
                .onFailure(System.out::println));
    }

    /**
     * Запрашивает актуальный список пользователей чата.
     */
    private void requestUsersList() {
        clientService.getUsersList(new ResponseConsumer()
                .onSuccess(ok -> {
                    UsersListResponseDto usersListResponseDto = (UsersListResponseDto) ok.getBody();
                    notifier.notifyUsersList(usersListResponseDto.usernames());
                }));
    }

    @Override
    public void sendMessage(String message) {
        clientService.sendMessage(message, new ResponseConsumer());
    }

    @Override
    public void disconnect(String cause) {
        log.info("Disconnect, because: {}", cause);
        try {
            clientService.disconnect();
        } catch (IOException e) {
            log.error("Error when disconnect. Error: {}", e.getMessage());
        }
    }

    @Override
    public void start() {
        notifier.notifyStartClient();
    }
}
