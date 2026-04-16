package ru.shift.controller;

import lombok.RequiredArgsConstructor;
import ru.shift.GameLevel;
import ru.shift.model.GameModel;

@RequiredArgsConstructor
public class MainGameController implements Controller {
    private final GameModel model;

    @Override
    public void startNewGame() {
        model.startNewGame();
    }

    @Override
    public void startNewGame(GameLevel gameLevel) {
        model.startNewGame(gameLevel);
    }

    @Override
    public void openCell(int x, int y) {
        model.openCell(x, y);
    }

    @Override
    public void openNeighboringCells(int x, int y) {
        model.openNeighboringCells(x, y);
    }

    @Override
    public void toggleFlag(int x, int y) {
        model.toggleFlag(x, y);
    }
}
