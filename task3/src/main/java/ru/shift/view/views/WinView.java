package ru.shift.view.views;

import ru.shift.controller.Controller;
import ru.shift.model.listeners.GameWonListener;
import ru.shift.view.windows.WinWindow;

import java.awt.Window;

public class WinView implements GameWonListener {
    private final WinWindow winWindow;

    public WinView(Window owner, Controller controller) {
        winWindow = new WinWindow(owner);
        winWindow.setNewGameAction(e -> controller.startNewGame());
        winWindow.setExitAction(e -> System.exit(0));
    }

    public void setVisible(boolean visible) {
        winWindow.setVisible(visible);
    }

    @Override
    public void onGameWon() {
        winWindow.setVisible(true);
    }
}
