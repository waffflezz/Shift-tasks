package ru.shift.client.model.contracts;

/**
 * Контракт чата - отправка сообщений.
 */
public interface ChatContract {
    /**
     * Отправляет текстовое сообщение в чат.
     *
     * @param message текст сообщения
     */
    void sendMessage(String message);
}
