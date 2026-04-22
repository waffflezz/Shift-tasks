package ru.shift.controller;

import ru.shift.GameLevel;

/**
 * Описывает пользовательские действия, доступные в основном контроллере.
 */
public interface Controller {
    /**
     * Запускает новую игру
     */
    void startNewGame();

    /**
     * Запускает новую игру с указанной сложностью.
     *
     * @param gameLevel выбранный уровень сложности
     */
    void startNewGame(GameLevel gameLevel);

    /**
     * Открывает указанную клетку.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     */
    void openCell(int x, int y);

    /**
     * Открывает соседние клетки вокруг указанной клетки.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     */
    void openNeighboringCells(int x, int y);

    /**
     * Переключает флаг на указанной клетке.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     */
    void toggleFlag(int x, int y);
}
