package ru.shift;

import ru.shift.controller.MainGameController;
import ru.shift.model.GameModel;
import ru.shift.model.MainModel;
import ru.shift.model.ModelObservers;
import ru.shift.model.listeners.BombsGeneratedListener;
import ru.shift.model.listeners.CellFlagChangedListener;
import ru.shift.model.listeners.CellOpenListener;
import ru.shift.model.listeners.GameLostListener;
import ru.shift.model.listeners.GameStartListener;
import ru.shift.model.listeners.GameWonListener;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observer.ObserversRegistry;
import ru.shift.view.views.MainView;

public class Main {
    public static void main(String[] args) {
        GameLevel initialLevel = GameLevel.NOVICE;
        ObserversRegistry<ModelListener> observersRegistry = new ModelObservers();

        GameModel model = new MainModel(
                initialLevel.getWidth(),
                initialLevel.getHeight(),
                initialLevel.getMinesCount(),
                observersRegistry
        );

        MainGameController controller = new MainGameController(model);
        MainView mainWindowView = new MainView(controller);

        observersRegistry.addListener(GameStartListener.class, mainWindowView);
        observersRegistry.addListener(CellOpenListener.class, mainWindowView);
        observersRegistry.addListener(BombsGeneratedListener.class, mainWindowView);
        observersRegistry.addListener(CellFlagChangedListener.class, mainWindowView);
        observersRegistry.addListener(GameLostListener.class, mainWindowView.getLoseWindowView());
        observersRegistry.addListener(GameWonListener.class, mainWindowView.getWinWindowView());

        controller.startNewGame();
    }
}
