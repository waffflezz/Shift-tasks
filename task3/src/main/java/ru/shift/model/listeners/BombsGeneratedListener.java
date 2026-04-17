package ru.shift.model.listeners;

import ru.shift.dto.BombsGeneratedDto;

/**
 * Получает уведомления о сгенерированных бомбах.
 */
@FunctionalInterface
public interface BombsGeneratedListener extends ModelListener {
    /**
     * Обрабатывает завершение генерации бомб.
     *
     * @param bombsGenerated данные о сгенерированных бомбах
     */
    void onBombsGenerated(BombsGeneratedDto bombsGenerated);
}
