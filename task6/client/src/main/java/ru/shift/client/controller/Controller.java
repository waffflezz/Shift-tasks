package ru.shift.client.controller;

public interface Controller {

    void connectToServer();

    void auth();

    void sendMessage();

    void disconnect();
}