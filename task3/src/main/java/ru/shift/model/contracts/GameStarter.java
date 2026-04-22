package ru.shift.model.contracts;

import ru.shift.GameLevel;

/**
 * Описывает операции запуска новой игры.
 */
public interface GameStarter {
    /**
     * Запускает новую игру с текущими настройками модели.
     */
    void startNewGame();

    /**
     * Запускает новую игру с указанным уровнем сложности.
     *
     * @param gameLevel выбранный уровень сложности
     */
    void startNewGame(GameLevel gameLevel);
}
