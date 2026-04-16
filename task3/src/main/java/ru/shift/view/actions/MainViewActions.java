package ru.shift.view.actions;

import ru.shift.view.views.CellClickHandler;

import java.awt.event.ActionListener;

public interface MainViewActions {
    void setNewGameMenuAction(ActionListener listener);
    void setHighScoresMenuAction(ActionListener listener);
    void setSettingsMenuAction(ActionListener listener);
    void setCellClickAction(CellClickHandler handler);
    void showHighScores();
    void showSettings();
    void dispose();

    SettingsViewActions settings();
    GameResultViewActions win();
    GameResultViewActions lose();
    RecordsViewActions records();
}
