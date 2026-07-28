package ru.shift.actors;

/**
 * Общий контракт для рабочих сущностей сценария.
 */
public interface Actor extends Runnable {
    /**
     * Запрашивает остановку рабочего потока.
     */
    void stop();
}
