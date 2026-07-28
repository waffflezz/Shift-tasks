package ru.shift.actors;

import lombok.extern.slf4j.Slf4j;
import ru.shift.storage.Storage;

/**
 * Производитель ресурсов, который с заданной периодичностью создает объекты и помещает их в хранилище.
 */
@Slf4j
public class Producer implements Actor {
    private static final String ID_PATTERN = "ПРОИЗВОДИТЕЛЬ %d.";

    private final String id;
    private final Storage storage;
    private final long producerTimeMillis;

    private volatile boolean running = true;

    /**
     * Создает производителя.
     *
     * @param id идентификатор производителя
     * @param storage общее хранилище ресурсов
     * @param producerTimeMillis время производства одного ресурса в миллисекундах
     */
    public Producer(int id, Storage storage, long producerTimeMillis) {
        this.id = ID_PATTERN.formatted(id);
        this.storage = storage;
        this.producerTimeMillis = producerTimeMillis;
    }

    /**
     * Выполняет основной цикл производства ресурсов до остановки потока или получения сигнала завершения.
     */
    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        log.info("{} начал работу", id);
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                log.info("{} производит ресурс", id);
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

    @Override
    public void stop() {
        running = false;
    }
}
