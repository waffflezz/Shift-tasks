package ru.shift.view.views;

import ru.shift.model.GameState;
import ru.shift.model.listeners.GameStateChangedListener;
import ru.shift.view.actions.GameResultViewActions;
import ru.shift.view.windows.WinWindow;

import javax.swing.*;
import java.awt.Window;
import java.awt.event.ActionListener;

public class WinView implements GameResultViewActions, GameStateChangedListener {
    private final WinWindow winWindow;

    public WinView(Window owner) {
        winWindow = new WinWindow(owner);
    }

    public void setVisible(boolean visible) {
        SwingUtilities.invokeLater(() -> winWindow.setVisible(visible));
    }

    @Override
    public void onGameStateChanged(GameState gameState) {
        if (gameState != GameState.WON) {
            return;
        }

        setVisible(true);
    }

    @Override
    public void setNewGameAction(ActionListener action) {
        winWindow.setNewGameAction(action);
    }

    @Override
    public void setExitAction(ActionListener action) {
        winWindow.setExitAction(action);
    }
}
