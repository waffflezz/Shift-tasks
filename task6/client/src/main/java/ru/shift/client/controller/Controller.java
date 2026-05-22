package ru.shift.client.controller;

public interface Controller {
    void start();

    void connectToServer(String ip, int port);

    void auth(String nickname);

    void sendMessage(String message);

    void disconnect();
}