package ru.shift;

import lombok.Getter;

public class Resource {
    private static final Object SYNC = new Object();
    private static int nextId = 1;

    @Getter
    private final int id;

    public Resource() {
        this.id = incrementId();
    }

    private static int incrementId() {
        synchronized (SYNC) {
            return nextId++;
        }
    }
}
