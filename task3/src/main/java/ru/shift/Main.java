package ru.shift;

import ru.shift.controller.MainGameController;
import ru.shift.external.ExternalObservers;
import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.score.Score;
import ru.shift.external.score.ScoreRepository;
import ru.shift.external.timer.Timer;
import ru.shift.model.GameModel;
import ru.shift.model.MainModel;
import ru.shift.model.ModelObservers;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observer.ObserversRegistry;
import ru.shift.view.MainView;

import java.nio.file.Path;

public class Main {
    private static final String SCORE_PATH = "scores.json";

    public static void main(String[] args) {
        GameLevel initialLevel = GameLevel.NOVICE;
        ObserversRegistry<ModelListener> modelObservers = new ModelObservers();
        ObserversRegistry<ExternalListener> externalObservers = new ExternalObservers();

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
