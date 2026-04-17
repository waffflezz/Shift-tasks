package ru.shift;

import ru.shift.controller.MainGameController;
import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.score.Score;
import ru.shift.external.score.ScoreRepository;
import ru.shift.external.timer.Timer;
import ru.shift.model.GameModel;
import ru.shift.model.MainModel;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observers.ObserversByTypeRegistry;
import ru.shift.view.MainView;

import java.nio.file.Path;

/**
 * Точка входа в приложение.
 */
public class Main {
    private static final String SCORE_PATH = "scores.json";

    /**
     * Создаёт компоненты приложения и запускает начальную игру.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        GameLevel initialLevel = GameLevel.NOVICE;

        var modelObservers = new ObserversByTypeRegistry<ModelListener>();
        var externalObservers = new ObserversByTypeRegistry<ExternalListener>();

        Timer timer = new Timer(externalObservers, modelObservers);
        Score score = new Score(new ScoreRepository(Path.of(SCORE_PATH)), modelObservers, externalObservers, timer);

        GameModel model = new MainModel(
                initialLevel.getWidth(),
                initialLevel.getHeight(),
                initialLevel.getMinesCount(),
                modelObservers
        );
        MainView view = new MainView(modelObservers, externalObservers);
        MainGameController controller = new MainGameController(model, view, score);

        score.publishHighScores();
        controller.startNewGame();
    }
}
