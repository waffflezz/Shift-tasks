package ru.shift.view.views;

import ru.shift.GameLevel;
import ru.shift.view.types.GameType;
import ru.shift.view.actions.SettingsViewActions;
import ru.shift.view.windows.SettingsWindow;

import java.awt.Window;
import java.util.function.Consumer;

public class SettingsView implements SettingsViewActions {
    private final SettingsWindow settingsWindow;

    public SettingsView(Window owner) {
        settingsWindow = new SettingsWindow(owner);
    }

    @Override
    public void setStartNewGameAction(Consumer<GameLevel> action) {
        settingsWindow.setApplyHandler(gameType -> action.accept(toGameLevel(gameType)));
    }

    public void setVisible(boolean visible) {
        settingsWindow.setVisible(visible);
    }

    private GameLevel toGameLevel(GameType gameType) {
        return GameLevel.valueOf(gameType.name());
    }

}
