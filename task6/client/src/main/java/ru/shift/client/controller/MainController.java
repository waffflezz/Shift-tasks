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
    public void connectToServer() {

    }

    @Override
    public void auth() {

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void disconnect() {
//        model.disconnect();
    }

    private void bindView() {
        view.join().setConnectionAction(e -> connectToServer());

        view.auth().setAuthAction(e -> auth());

        view.chat().setSendMessageAction(e -> sendMessage());

        view.chat().setMessageInputAction(e -> sendMessage());

        view.chat().setWindowCloseAction(e -> disconnect());
    }
}