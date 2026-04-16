package ru.shift.model.listeners;

@FunctionalInterface
public interface GameWonListener extends ModelListener {
    void onGameWon();
}
