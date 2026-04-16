package ru.shift.model.listeners;

import ru.shift.dto.GameStartedDto;

@FunctionalInterface
public interface GameStartListener extends ModelListener {
    void onGameStarted(GameStartedDto gameStarted);
}
