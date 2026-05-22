package ru.shift.client.controller;

import ru.shift.client.model.ChatModel;
import ru.shift.client.view.actions.MainViewActions;

public class MainController implements Controller {
    private final ChatModel model;
    private final MainViewActions view;

    public MainController(
            ChatModel model,
            MainViewActions view
    ) {
        this.model = model;
        this.view = view;

        bindView();
    }

    @Override
    public void start() {
        model.start();
    }

    @Override
    public void connectToServer(String ip, int port) {
        model.connect(ip, port);
    }

    @Override
    public void auth(String nickname) {
        model.auth(nickname);
    }

    @Override
    public void sendMessage(String message) {
        model.sendMessage(message);
    }

    @Override
    public void disconnect() {
        model.disconnect("Client close");
    }

    private void bindView() {
        view.join().setConnectionAction(this::connectToServer);
        view.auth().setAuthAction(this::auth);
        view.setSendMessageAction(this::sendMessage);
    }
}