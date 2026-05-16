package ru.shift.client.model.listeners;

import ru.shift.client.dto.SomeDto;

/**
 * Получает уведомления о сгенерированных бомбах.
 */
@FunctionalInterface
public interface SomeListener extends ModelListener {
    /**
     * Обрабатывает завершение генерации бомб.
     *
     * @param bombsGenerated данные о сгенерированных бомбах
     */
    void onFire(SomeDto bombsGenerated);
}
