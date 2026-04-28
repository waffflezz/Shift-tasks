package ru.shift;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.extern.slf4j.Slf4j;

/**
 * Потокобезопасное хранилище ресурсов ограниченного размера.
 */
@Slf4j
public class Storage {
    private final Object isNotFull = new Object();
    private final Object isNotEmpty = new Object();

    private final Queue<Resource> queue = new ArrayDeque<>();
    private final int maxSize;

    /**
     * Создает хранилище с ограничением по количеству ресурсов.
     *
     * @param maxSize максимальный размер очереди
     */
    public Storage(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Помещает ресурс в хранилище, ожидая освобождения места при переполнении.
     *
     * @param producerName имя производителя, используемое в логах
     * @param resource добавляемый ресурс
     * @throws InterruptedException если ожидание свободного места было прервано
     */
    public void put(String producerName, Resource resource) throws InterruptedException {
        log.info("{} хочет положить ресурс {}", producerName, resource.getId());

        while (true) {
            boolean added = false;
            int currentSize;

            synchronized (queue) {
                if (queue.size() < maxSize) {
                    queue.add(resource);
                    added = true;
                    currentSize = queue.size();
                    log.info(
                        "{} положил ресурс {}. текущий размер: {}/{}",
                        producerName,
                        resource.getId(),
                        currentSize,
                        maxSize
                    );
                } else {
                    currentSize = queue.size();
                }
            }

            if (added) {
                notifyIsNotEmpty();
                return;
            }

            synchronized (isNotFull) {
                synchronized (queue) {
                    if (queue.size() < maxSize) {
                        continue;
                    }

                    log.info(
                            "{} ожидает: хранилище полное. Текущий размер: {}/{}",
                            producerName,
                            currentSize,
                            maxSize
                    );
                }
                isNotFull.wait();
            }
        }
    }

    /**
     * Извлекает ресурс из хранилища, ожидая появления данных при пустой очереди.
     *
     * @param consumerName имя потребителя, используемое в логах
     * @return извлеченный ресурс
     * @throws InterruptedException если ожидание ресурса было прервано
     */
    public Resource get(String consumerName) throws InterruptedException {
        while (true) {
            Resource resource = null;
            int currentSize;

            synchronized (queue) {
                if (!queue.isEmpty()) {
                    resource = queue.remove();
                    currentSize = queue.size();
                    log.info(
                        "{} взял ресурс {} из хранилища. Текущий размер: {}/{}",
                        consumerName,
                        resource.getId(),
                        currentSize,
                        maxSize
                    );
                } else {
                    currentSize = queue.size();
                }
            }

            if (resource != null) {
                notifyIsNotFull();
                return resource;
            }

            synchronized (isNotEmpty) {
                synchronized (queue) {
                    if (!queue.isEmpty()) {
                        continue;
                    }

                    log.info(
                            "{} ожидает: хранилище пустое. Текущий размер: {}/{}",
                            consumerName,
                            currentSize,
                            maxSize
                    );
                }
                isNotEmpty.wait();
            }
        }
    }

    /**
     * Уведомляет ожидающий поток о появлении свободного места в хранилище.
     */
    private void notifyIsNotFull() {
        synchronized (isNotFull) {
            isNotFull.notify();
        }
    }

    /**
     * Уведомляет ожидающий поток о появлении хотя бы одного ресурса в хранилище.
     */
    private void notifyIsNotEmpty() {
        synchronized (isNotEmpty) {
            isNotEmpty.notify();
        }
    }
}
