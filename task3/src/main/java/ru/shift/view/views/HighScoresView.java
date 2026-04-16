package ru.shift.view.views;

import ru.shift.controller.Controller;
import ru.shift.view.windows.HighScoresWindow;

import java.awt.Window;

public class HighScoresView {
    private final HighScoresWindow highScoresWindow;

    public HighScoresView(Window owner, Controller controller) {
        highScoresWindow = new HighScoresWindow(owner);
    }

    public void setVisible(boolean visible) {
        highScoresWindow.setVisible(visible);
    }

    public void setNoviceRecord(String winnerName, int timeValue) {
        highScoresWindow.setNoviceRecord(winnerName, timeValue);
    }

    public void setMediumRecord(String winnerName, int timeValue) {
        highScoresWindow.setMediumRecord(winnerName, timeValue);
    }

    public void setExpertRecord(String winnerName, int timeValue) {
        highScoresWindow.setExpertRecord(winnerName, timeValue);
    }
}
