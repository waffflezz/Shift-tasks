package ru.shift.view.views;

import ru.shift.dto.HighScoresDto;
import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.listeners.HighScoresListener;
import ru.shift.observer.ObserversRegistry;
import ru.shift.view.windows.HighScoresWindow;

import javax.swing.*;
import java.awt.Window;

public class HighScoresView implements HighScoresListener {
    private final HighScoresWindow highScoresWindow;

    public HighScoresView(Window owner, ObserversRegistry<ExternalListener> externalObservers) {
        highScoresWindow = new HighScoresWindow(owner);
        bindObservers(externalObservers);
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

    @Override
    public void onHighScoresChanged(HighScoresDto highScores) {
        Runnable updateAction = () -> {
            setNoviceRecord(highScores.noviceRecord().playerName(), highScores.noviceRecord().timeValue());
            setMediumRecord(highScores.mediumRecord().playerName(), highScores.mediumRecord().timeValue());
            setExpertRecord(highScores.expertRecord().playerName(), highScores.expertRecord().timeValue());
        };

        updateAction.run();
    }

    private void bindObservers(ObserversRegistry<ExternalListener> externalObservers) {
        externalObservers.addListener(HighScoresListener.class, this);
    }
}
