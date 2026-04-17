package ru.shift.view.views;

import ru.shift.GameLevel;
import ru.shift.view.types.GameType;
import ru.shift.view.actions.SettingsViewActions;
import ru.shift.view.windows.SettingsWindow;

import java.awt.Window;
import java.util.function.Consumer;

/**
 * Адаптирует диалог настроек к действиям представления.
 */
public class SettingsView implements SettingsViewActions {
    private final SettingsWindow settingsWindow;

    private GameLevel currentGameLevel = GameLevel.NOVICE;

    /**
     * Создаёт представление настроек.
     *
     * @param owner родительское окно
     */
    public SettingsView(Window owner) {
        settingsWindow = new SettingsWindow(owner);
    }

    @Override
    public void setStartNewGameAction(Consumer<GameLevel> action) {
        settingsWindow.setApplyHandler(gameType -> {
            GameLevel gameLevel = toGameLevel(gameType);
            currentGameLevel = gameLevel;
            action.accept(gameLevel);
        });
    }

    /**
     * Показывает или скрывает диалог настроек.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        if (visible) {
            settingsWindow.setSelectedGameType(GameType.valueOf(currentGameLevel.name()));
        }
        settingsWindow.setVisible(visible);
    }

    /**
     * Преобразует тип игры из интерфейса в доменный уровень игры.
     *
     * @param gameType выбранный тип игры из интерфейса
     * @return соответствующий доменный уровень игры
     */
    private GameLevel toGameLevel(GameType gameType) {
        return GameLevel.valueOf(gameType.name());
    }

}
