package ru.shift.factories;

import ru.shift.actors.Actor;
import ru.shift.actors.Consumer;
import ru.shift.storage.Storage;

/**
 * Фабрика для создания потребителей.
 */
public class ConsumerFactory implements ActorFactory {
    @Override
    public Actor create(int id, Storage storage, long workTimeMillis) {
        return new Consumer(id, storage, workTimeMillis);
    }
}
