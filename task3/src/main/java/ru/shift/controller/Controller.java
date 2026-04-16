package ru.shift.controller;

import ru.shift.GameLevel;

public interface Controller {
    void startNewGame();

    void startNewGame(GameLevel gameLevel);

    void openCell(int x, int y);

    void openNeighboringCells(int x, int y);

    void toggleFlag(int x, int y);
}
