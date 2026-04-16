package ru.shift.view.views;

import ru.shift.GameLevel;
import ru.shift.controller.Controller;
import ru.shift.view.types.GameType;
import ru.shift.view.windows.SettingsWindow;

import java.awt.Window;

public class SettingsView {
    private final SettingsWindow settingsWindow;

    public SettingsView(Window owner, Controller controller) {
        settingsWindow = new SettingsWindow(owner);
        settingsWindow.setApplyHandler(gameType -> controller.startNewGame(toGameLevel(gameType)));
    }

    public void setVisible(boolean visible) {
        settingsWindow.setVisible(visible);
    }

    public void setSelectedGameType(GameType gameType) {
        settingsWindow.setSelectedGameType(gameType);
    }

    private GameLevel toGameLevel(GameType gameType) {
        return GameLevel.valueOf(gameType.name());
    }
}
