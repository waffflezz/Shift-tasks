package ru.shift.view.views;

import ru.shift.dto.HighScoresDto;
import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.listeners.HighScoresListener;
import ru.shift.observers.ObserversRegistry;
import ru.shift.view.windows.HighScoresWindow;

import java.awt.Window;

/**
 * Обновляет окно рекордов по внешним событиям.
 */
public class HighScoresView implements HighScoresListener {
    private final HighScoresWindow highScoresWindow;

    /**
     * Создаёт представление рекордов и подписывает его на обновления.
     *
     * @param owner родительское окно
     * @param externalObservers реестр внешних наблюдателей
     */
    public HighScoresView(Window owner, ObserversRegistry<ExternalListener> externalObservers) {
        highScoresWindow = new HighScoresWindow(owner);
        bindObservers(externalObservers);
    }

    /**
     * Показывает или скрывает диалог рекордов.
     *
     * @param visible признак видимости
     */
    public void setVisible(boolean visible) {
        highScoresWindow.setVisible(visible);
    }

    /**
     * Обновляет текст рекорда для лёгкого режима.
     *
     * @param winnerName имя игрока
     * @param timeValue время рекорда
     */
    public void setNoviceRecord(String winnerName, int timeValue) {
        highScoresWindow.setNoviceRecord(winnerName, timeValue);
    }

    /**
     * Обновляет текст рекорда для среднего режима.
     *
     * @param winnerName имя игрока
     * @param timeValue время рекорда
     */
    public void setMediumRecord(String winnerName, int timeValue) {
        highScoresWindow.setMediumRecord(winnerName, timeValue);
    }

    /**
     * Обновляет текст рекорда для сложного режима.
     *
     * @param winnerName имя игрока
     * @param timeValue время рекорда
     */
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

    /**
     * Подписывает это представление на обновления рекордов.
     *
     * @param externalObservers реестр внешних наблюдателей
     */
    private void bindObservers(ObserversRegistry<ExternalListener> externalObservers) {
        externalObservers.addListener(HighScoresListener.class, this);
    }
}
