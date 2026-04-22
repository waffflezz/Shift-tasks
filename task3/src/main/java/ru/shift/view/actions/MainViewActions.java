package ru.shift.view.actions;

import ru.shift.view.views.CellClickHandler;

import java.awt.event.ActionListener;

/**
 * Описывает операции, доступные через основное представление.
 */
public interface MainViewActions {
    /**
     * Устанавливает обработчик для пункта меню новой игры.
     *
     * @param listener обработчик действия
     */
    void setNewGameMenuAction(ActionListener listener);

    /**
     * Устанавливает обработчик для пункта меню рекордов.
     *
     * @param listener обработчик действия
     */
    void setHighScoresMenuAction(ActionListener listener);

    /**
     * Устанавливает обработчик для пункта меню настроек.
     *
     * @param listener обработчик действия
     */
    void setSettingsMenuAction(ActionListener listener);

    /**
     * Устанавливает обработчик кликов по клеткам поля.
     *
     * @param handler обработчик кликов
     */
    void setCellClickAction(CellClickHandler handler);

    /**
     * Показывает окно рекордов.
     */
    void showHighScores();

    /**
     * Показывает окно настроек.
     */
    void showSettings();

    /**
     * Освобождает ресурсы основного представления.
     */
    void dispose();

    /**
     * Возвращает доступ к действиям представления настроек.
     *
     * @return фасад представления настроек
     */
    SettingsViewActions settings();

    /**
     * Возвращает доступ к действиям окна победы.
     *
     * @return фасад окна победы
     */
    GameResultViewActions win();

    /**
     * Возвращает доступ к действиям окна поражения.
     *
     * @return фасад окна поражения
     */
    GameResultViewActions lose();

    /**
     * Возвращает доступ к действиям сохранения рекорда.
     *
     * @return фасад окна рекордов
     */
    RecordsViewActions records();
}
