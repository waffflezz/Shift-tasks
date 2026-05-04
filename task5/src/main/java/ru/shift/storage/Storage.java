package ru.shift.storage;

import ru.shift.actors.Resource;

/**
 * Контракт хранилища ресурсов для производителей и потребителей.
 */
public interface Storage {
    /**
     * Помещает ресурс в хранилище.
     *
     * @param producerName имя производителя, используемое в логах
     * @param resource добавляемый ресурс
     * @throws InterruptedException если ожидание свободного места было прервано
     */
    void put(String producerName, Resource resource) throws InterruptedException;

    /**
     * Извлекает ресурс из хранилища.
     *
     * @param consumerName имя потребителя, используемое в логах
     * @return извлеченный ресурс
     * @throws InterruptedException если ожидание ресурса было прервано
     */
    Resource get(String consumerName) throws InterruptedException;
}