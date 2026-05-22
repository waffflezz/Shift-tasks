package ru.shift.common.exceptions;

/**
 * Исключение при ошибке сериализации или десериализации сообщения.
 */
public class SerializeException extends Exception {
    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
