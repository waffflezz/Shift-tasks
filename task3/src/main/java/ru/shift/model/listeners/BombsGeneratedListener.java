package ru.shift.model.listeners;

import ru.shift.dto.BombsGeneratedDto;

@FunctionalInterface
public interface BombsGeneratedListener extends ModelListener {
    void onBombsGenerated(BombsGeneratedDto bombsGenerated);
}
