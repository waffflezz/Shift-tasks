package ru.shift.external.listeners;

/**
 * Получает уведомления о новом рекорде.
 */
@FunctionalInterface
public interface NewRecordListener extends ExternalListener {
    /**
     * Обрабатывает событие нового рекорда.
     */
    void onNewRecord();
}
