package ru.shift.client.view.views.handlers;

/**
 * Обработчик отправки сообщения.
 */
@FunctionalInterface
public interface MessageHandler {
    /**
     * Вызывается при отправке текстового сообщения.
     *
     * @param message текст сообщения
     */
    void handle(String message);
}
