package ru.shift.model.listeners;

import ru.shift.model.GameState;

/**
 * Получает уведомления об изменении состояния игры.
 */
@FunctionalInterface
public interface GameStateChangedListener extends ModelListener {
    /**
     * Обрабатывает новое состояние игры.
     *
     * @param gameState текущее состояние игры
     */
    void onGameStateChanged(GameState gameState);
}
