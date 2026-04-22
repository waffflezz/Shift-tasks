package ru.shift.view.actions;

import java.awt.event.ActionListener;

/**
 * Описывает действия, доступные в диалоге результата игры.
 */
public interface GameResultViewActions {
    /**
     * Устанавливает обработчик запуска новой игры.
     *
     * @param action обработчик действия
     */
    void setNewGameAction(ActionListener action);

    /**
     * Устанавливает обработчик закрытия приложения.
     *
     * @param action обработчик действия
     */
    void setExitAction(ActionListener action);
}
