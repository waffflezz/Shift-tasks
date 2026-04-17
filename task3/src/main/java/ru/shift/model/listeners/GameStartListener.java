package ru.shift.model.listeners;

import ru.shift.dto.GameStartedDto;

/**
 * Получает уведомления о запуске новой игры.
 */
@FunctionalInterface
public interface GameStartListener extends ModelListener {
    /**
     * Обрабатывает событие запуска игры.
     *
     * @param gameStarted данные о запуске игры
     */
    void onGameStarted(GameStartedDto gameStarted);
}
