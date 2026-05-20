package ru.shift.client.view;

import lombok.Getter;
import ru.shift.client.dto.AuthDto;
import ru.shift.client.dto.ConnectionStatusDto;
import ru.shift.client.dto.JoinUserDto;
import ru.shift.client.model.listeners.AuthListener;
import ru.shift.client.model.listeners.ConnectionListener;
import ru.shift.client.model.listeners.JoinUserListener;
import ru.shift.client.model.listeners.ModelListener;
import ru.shift.client.model.listeners.StartClientListener;
import ru.shift.client.observers.ObserversRegistry;
import ru.shift.client.view.actions.AuthViewAction;
import ru.shift.client.view.actions.ChatViewAction;
import ru.shift.client.view.actions.JoinViewActions;
import ru.shift.client.view.actions.MainViewActions;
import ru.shift.client.view.views.AuthView;
import ru.shift.client.view.views.JoinView;
import ru.shift.client.view.windows.MainWindow;

/**
 * Основной слой представления, обновляющий главное игровое окно.
 */
public class MainView implements MainViewActions, StartClientListener, ConnectionListener, AuthListener,
        JoinUserListener {
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
    public ChatViewAction chat() {
        return null;
    }

    @Override
    public void dispose() {

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
    }
}
