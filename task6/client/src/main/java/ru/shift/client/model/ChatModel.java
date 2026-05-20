package ru.shift.client.model;

import ru.shift.client.model.contracts.ChatContract;
import ru.shift.client.model.contracts.JoinContract;

/**
 * Объединяет все операции модели, доступные контроллеру.
 */
public interface ChatModel extends JoinContract, ChatContract {
    void start();
}
