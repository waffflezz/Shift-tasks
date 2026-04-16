package ru.shift.model.listeners;

@FunctionalInterface
public interface GameLostListener extends ModelListener {
    void onGameLost();
}
