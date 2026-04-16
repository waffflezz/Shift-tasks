package ru.shift.model.listeners;

import ru.shift.model.GameState;

@FunctionalInterface
public interface GameStateChangedListener extends ModelListener {
    void onGameStateChanged(GameState gameState);
}
