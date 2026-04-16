package ru.shift.view.actions;

import ru.shift.GameLevel;

import java.util.function.Consumer;

public interface SettingsViewActions {
    void setStartNewGameAction(Consumer<GameLevel> action);
}
