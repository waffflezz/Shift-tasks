package ru.shift.view.views;

import ru.shift.controller.Controller;
import ru.shift.model.listeners.GameLostListener;
import ru.shift.view.windows.LoseWindow;

import java.awt.Window;

public class LoseView implements GameLostListener {
    private final LoseWindow loseWindow;

    public LoseView(Window owner, Controller controller) {
        loseWindow = new LoseWindow(owner);
        loseWindow.setNewGameAction(e -> controller.startNewGame());
        loseWindow.setExitAction(e -> System.exit(0));
    }

    public void setVisible(boolean visible) {
        loseWindow.setVisible(visible);
    }

    @Override
    public void onGameLost() {
        loseWindow.setVisible(true);
    }
}
