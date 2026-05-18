package ru.shift.storage;

import java.util.ArrayDeque;
import java.util.Queue;

import lombok.extern.slf4j.Slf4j;
import ru.shift.actors.Resource;

/**
 * Потокобезопасное хранилище ресурсов ограниченного размера.
 */
@Slf4j
public class BoundedStorage implements Storage {
    private final Object isNotFull = new Object();
    private final Object isNotEmpty = new Object();

    private final Queue<Resource> queue = new ArrayDeque<>();
    private final int maxSize;

    /**
     * Создает хранилище с ограничением по количеству ресурсов.
     *
     * @param maxSize максимальный размер очереди
     */
    public BoundedStorage(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void put(String producerName, Resource resource) throws InterruptedException {
        log.info("{} хочет положить ресурс {}", producerName, resource.getId());

        int currentSize;
        while (true) {
            synchronized (queue) {
                if (queue.size() < maxSize) {
                    queue.add(resource);
                    currentSize = queue.size();
                    log.info(
                        "{} положил ресурс {}. текущий размер: {}/{}",
                        producerName,
                        resource.getId(),
                        currentSize,
                        maxSize
                    );
                    notifyIsNotEmpty();
                    return;
                } else {
                    currentSize = queue.size();
                }
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

    @Override
    public Resource get(String consumerName) throws InterruptedException {
        Resource resource;
        int currentSize;
        while (true) {
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
                    notifyIsNotFull();
                    return resource;
                } else {
                    currentSize = queue.size();
                }
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
