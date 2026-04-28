package ru.shift;

import lombok.Getter;

/**
 * Ресурс, передаваемый от производителей к потребителям.
 */
public class Resource {
    private static final Object SYNC = new Object();
    private static int nextId = 1;

    @Getter
    private final int id;

    /**
     * Создает ресурс и назначает ему уникальный идентификатор.
     */
    public Resource() {
        this.id = incrementId();
    }

    /**
     * Генерирует следующий уникальный идентификатор ресурса.
     *
     * @return новый идентификатор
     */
    private static int incrementId() {
        synchronized (SYNC) {
            return nextId++;
        }
    }
}
