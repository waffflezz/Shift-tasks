package ru.shift.client.model.contracts;

public interface JoinContract {
    void connect(String ip, int port);
    void auth(String username);
    void disconnect();
}
