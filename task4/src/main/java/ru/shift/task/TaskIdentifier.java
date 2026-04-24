package ru.shift.task;

/**
 * Контракт для задач, которым можно сопоставить уникальный идентификатор.
 */
public interface TaskIdentifier {
    /**
     * Возвращает идентификатор задачи.
     *
     * @return идентификатор задачи
     */
    int getId();
}
