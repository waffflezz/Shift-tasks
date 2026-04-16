package ru.shift.model.contracts;

import ru.shift.GameLevel;

public interface GameStarter {
    void startNewGame();

    void startNewGame(GameLevel gameLevel);
}
