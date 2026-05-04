package ru.shift.factories;

import ru.shift.actors.Actor;
import ru.shift.actors.Producer;
import ru.shift.storage.Storage;

/**
 * Фабрика для создания производителей.
 */
public class ProducerFactory implements ActorFactory {
    @Override
    public Actor create(int id, Storage storage, long workTimeMillis) {
        return new Producer(id, storage, workTimeMillis);
    }
}
