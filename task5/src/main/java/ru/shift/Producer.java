package ru.shift;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Producer implements Runnable {
    private final static String ID_PATTERN = "ПРОИЗВОДИТЕЛЬ %d.";

    private final String id;
    private final Storage storage;
    private final long producerTimeMillis;

    private volatile boolean running = true;

    public Producer(int id, Storage storage, long producerTimeMillis) {
        this.id = ID_PATTERN.formatted(id);
        this.storage = storage;
        this.producerTimeMillis = producerTimeMillis;
    }

    @Override
    public void run() {
        log.info("{} начал работу", id);
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                log.info("{} производит ресурс", id);
                //noinspection BusyWait
                Thread.sleep(producerTimeMillis);
                Resource resource = new Resource();
                log.info("{} произвел ресурс {}", id, resource.getId());
                storage.put(id, resource);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
