package ru.shift.client.exceptions;

public class ClientConnectionException extends Exception {
    public ClientConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
