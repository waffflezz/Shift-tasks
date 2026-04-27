package ru.shift;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer implements Runnable {
    private static final String ID_PATTERN = "ПОТРЕБИТЕЛЬ %d.";

    private final String id;
    private final Storage storage;
    private final long consumerTimeMillis;

    private volatile boolean running = true;

    public Consumer(int id, Storage storage, long consumerTimeMillis) {
        this.id = ID_PATTERN.formatted(id);
        this.storage = storage;
        this.consumerTimeMillis = consumerTimeMillis;
    }

    @Override
    public void run() {
        log.info("{} начал работу", id);

        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Resource resource = storage.get(id);
                log.info("{} потребляет ресурс {}", id, resource.getId());

                //noinspection BusyWait
                Thread.sleep(consumerTimeMillis);
                log.info("{} потребил ресурс {}", id, resource.getId());
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
