package ru.shift.view.actions;

import ru.shift.GameLevel;

import java.util.function.Consumer;

/**
 * Описывает действия, доступные в представлении настроек.
 */
public interface SettingsViewActions {
    /**
     * Устанавливает обработчик запуска новой игры с выбранным уровнем.
     *
     * @param action обработчик применения
     */
    void setStartNewGameAction(Consumer<GameLevel> action);
}
