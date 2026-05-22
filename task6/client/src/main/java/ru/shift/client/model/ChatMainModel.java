package ru.shift.client.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.shift.client.dto.AuthDto;
import ru.shift.client.dto.ConnectionStatusDto;
import ru.shift.client.dto.JoinUserDto;
import ru.shift.client.dto.LeftUserDto;
import ru.shift.client.dto.MessageDto;
import ru.shift.client.model.connection.ClientService;
import ru.shift.client.model.connection.ResponseConsumer;
import ru.shift.client.model.listeners.AuthListener;
import ru.shift.client.model.listeners.ConnectionListener;
import ru.shift.client.model.listeners.DisconnectListener;
import ru.shift.client.model.listeners.JoinUserListener;
import ru.shift.client.model.listeners.LeftUserListener;
import ru.shift.client.model.listeners.MessageListener;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.model.listeners.StartClientListener;
import ru.shift.client.model.listeners.UsersListListener;
import ru.shift.client.observers.ObserversRegistry;
import ru.shift.common.protocol.dto.notification.DisconnectNotificationDto;
import ru.shift.common.protocol.dto.notification.JoinNotificationDto;
import ru.shift.common.protocol.dto.notification.LeftNotificationDto;
import ru.shift.common.protocol.dto.notification.MessageNotificationDto;
import ru.shift.common.protocol.dto.response.ErrorResponseDto;
import ru.shift.common.protocol.dto.response.LoginResponseDto;
import ru.shift.common.protocol.dto.response.MessageResponseDto;
import ru.shift.common.protocol.dto.response.UsersListResponseDto;

import java.io.IOException;

@Slf4j
public class ChatMainModel implements ChatModel {
    private final ClientService clientService;
    private final Notifier notifier;

    public ChatMainModel(ObserversRegistry<ModelListener> observers, ClientService clientService) {
        this.clientService = clientService;
        this.notifier = new Notifier(observers);
    }

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
        clientService.connect(ip, port)
                .thenAccept(ok -> {
                    initServerNotification();
                    notifier.notifyConnection(true, "Success connection");
                })
                .exceptionally(e -> {
                    notifier.notifyConnection(false, "Error connection. Wrong address or port");
                    return null;
                });
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
