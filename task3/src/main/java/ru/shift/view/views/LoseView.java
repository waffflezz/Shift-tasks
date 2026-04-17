package ru.shift.view.views;

import ru.shift.model.GameState;
import ru.shift.model.listeners.GameStateChangedListener;
import ru.shift.view.actions.GameResultViewActions;
import ru.shift.view.windows.LoseWindow;

import javax.swing.*;
import java.awt.Window;
import java.awt.event.ActionListener;

/**
 * Показывает окно поражения, когда игрок проигрывает.
 */
public class LoseView implements GameResultViewActions, GameStateChangedListener {
    private final LoseWindow loseWindow;

    /**
     * Создаёт представление окна поражения.
     *
     * @param owner родительское окно
     */
    public LoseView(Window owner) {
        loseWindow = new LoseWindow(owner);
    }

    /**
     * Показывает или скрывает окно поражения.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        SwingUtilities.invokeLater(() -> loseWindow.setVisible(visible));
    }

    @Override
    public void onGameStateChanged(GameState gameState) {
        if (gameState != GameState.LOST) {
            return;
        }

        setVisible(true);
    }

    @Override
    public void setNewGameAction(ActionListener action) {
        loseWindow.setNewGameAction(action);
    }

    @Override
    public void setExitAction(ActionListener action) {
        loseWindow.setExitAction(action);
    }
}
