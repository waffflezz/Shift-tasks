package ru.shift.server;

public interface Server {
    /**
     * Запускает сервер.
     */
    void start();

    /**
     * shutdown-hook для корректного завершения при остановке JVM.
     */
    void shutdown();
}
