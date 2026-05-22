package ru.shift.client.view;

import lombok.Getter;
import ru.shift.client.dto.AuthDto;
import ru.shift.client.dto.ConnectionStatusDto;
import ru.shift.client.dto.JoinUserDto;
import ru.shift.client.dto.LeftUserDto;
import ru.shift.client.dto.MessageDto;
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
import ru.shift.client.view.actions.AuthViewAction;
import ru.shift.client.view.actions.JoinViewActions;
import ru.shift.client.view.actions.MainViewActions;
import ru.shift.client.view.views.AuthView;
import ru.shift.client.view.views.JoinView;
import ru.shift.client.view.views.handlers.MessageHandler;
import ru.shift.client.view.windows.MainWindow;

import java.util.List;

/**
 * Основной слой представления, обновляющий главное игровое окно.
 */
public class MainView implements MainViewActions,
        StartClientListener, ConnectionListener, AuthListener,
        JoinUserListener, UsersListListener, DisconnectListener, LeftUserListener, MessageListener {
    private final MainWindow mainWindow;
    @Getter
    private final JoinView joinView;
    @Getter
    private final AuthView authView;

    /**
     * Создаёт основное представление и подписывает его на события модели и внешние события.
     *
     * @param modelObservers реестр наблюдателей модели
     */
    public MainView(
            ObserversRegistry<ModelListener> modelObservers
    ) {
        this.mainWindow = new MainWindow();
        this.joinView = new JoinView();
        this.authView = new AuthView();

        bindObservers(modelObservers);
    }

    /**
     * Показывает или скрывает главное окно.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        mainWindow.setVisible(visible);
    }

    @Override
    public JoinViewActions join() {
        return joinView;
    }

    @Override
    public AuthViewAction auth() {
        return authView;
    }

    @Override
    public void setSendMessageAction(MessageHandler handler) {
        mainWindow.setMessageHandler(handler);
    }

    @Override
    public void onStart() {
        joinView.setVisible(true);
    }

    @Override
    public void onConnection(ConnectionStatusDto connectionStatusDto) {
        if (!connectionStatusDto.success()) {
            joinView.showError(connectionStatusDto.message());
            return;
        }

        joinView.setVisible(false);
        authView.setVisible(true);
    }

    @Override
    public void onAuth(AuthDto authDto) {
        if (!authDto.success()) {
            authView.showError(authDto.errorMessage());
            return;
        }

        authView.setVisible(false);
        mainWindow.setVisible(true);
    }

    @Override
    public void onJoin(JoinUserDto joinUserDto) {
        var username = joinUserDto.username();
        var time = joinUserDto.time();

        mainWindow.addUser(username);
        mainWindow.addSystemMessage("Пользователь " + username + " подключился к чату", time);
    }

    @Override
    public void onUsersList(List<String> usernames) {
        mainWindow.setUsers(usernames);
    }

    @Override
    public void onDisconnect() {
        joinView.dispose();
        authView.dispose();
        mainWindow.dispose();
    }

    @Override
    public void onLeftUser(LeftUserDto leftUserDto) {
        var username = leftUserDto.username();
        var time = leftUserDto.time();

        mainWindow.removeUser(username);
        mainWindow.addSystemMessage("Пользователь " + username + " отключился от чата", time);
    }

    @Override
    public void onMessage(MessageDto messageDto) {
        var username = messageDto.sender();
        var time = messageDto.time();
        var message = messageDto.message();

        mainWindow.clearInput();
        mainWindow.addUserMessage(username, message, time);
    }

    /**
     * Подписывает представление на все необходимые наблюдатели.
     *
     * @param modelObservers реестр наблюдателей модели
     */
    private void bindObservers(
            ObserversRegistry<ModelListener> modelObservers
    ) {
        modelObservers.addListener(StartClientListener.class, this);
        modelObservers.addListener(ConnectionListener.class, this);
        modelObservers.addListener(AuthListener.class, this);
        modelObservers.addListener(JoinUserListener.class, this);
        modelObservers.addListener(UsersListListener.class, this);
        modelObservers.addListener(DisconnectListener.class, this);
        modelObservers.addListener(LeftUserListener.class, this);
        modelObservers.addListener(MessageListener.class, this);
    }
}
