package ru.shift.client.view.views.handlers;

@FunctionalInterface
public interface ConnectionHandler {
    void handle(String ip, int port);
}
