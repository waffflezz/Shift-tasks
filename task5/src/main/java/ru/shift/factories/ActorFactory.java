package ru.shift.factories;

import ru.shift.actors.Actor;
import ru.shift.storage.Storage;

/**
 * Контракт фабрики для создания рабочих сущностей сценария.
 */
public interface ActorFactory {
    /**
     * Создает рабочего актора.
     *
     * @param id идентификатор актора
     * @param storage общее хранилище ресурсов
     * @param workTimeMillis задержка выполнения одной итерации работы
     * @return созданный актор
     */
    Actor create(int id, Storage storage, long workTimeMillis);
}
