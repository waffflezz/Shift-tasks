package ru.shift.controller;

import ru.shift.GameLevel;
import ru.shift.external.contracts.ScoreSaver;
import ru.shift.model.GameModel;
import ru.shift.view.types.ButtonType;
import ru.shift.view.actions.MainViewActions;

public class MainGameController implements Controller {
    private final GameModel model;
    private final ScoreSaver scoreSaver;
    private final MainViewActions view;

    public MainGameController(
            GameModel model,
            MainViewActions view,
            ScoreSaver scoreSaver
    ) {
        this.model = model;
        this.scoreSaver = scoreSaver;
        this.view = view;
        bindView();
    }

    @Override
    public void startNewGame() {
        model.startNewGame();
    }

    @Override
    public void startNewGame(GameLevel gameLevel) {
        model.startNewGame(gameLevel);
    }

    @Override
    public void openCell(int x, int y) {
        model.openCell(x, y);
    }

    @Override
    public void openNeighboringCells(int x, int y) {
        model.openNeighboringCells(x, y);
    }

    @Override
    public void toggleFlag(int x, int y) {
        model.toggleFlag(x, y);
    }

    private void bindView() {
        view.setNewGameMenuAction(e -> startNewGame());
        view.setHighScoresMenuAction(e -> view.showHighScores());
        view.setSettingsMenuAction(e -> view.showSettings());

        view.setCellClickAction((x, y, buttonType) -> {
            if (buttonType == ButtonType.LEFT_BUTTON) {
                openCell(x, y);
                return;
            }

            if (buttonType == ButtonType.MIDDLE_BUTTON) {
                openNeighboringCells(x, y);
                return;
            }

            if (buttonType == ButtonType.RIGHT_BUTTON) {
                toggleFlag(x, y);
            }
        });

        view.settings().setStartNewGameAction(this::startNewGame);
        view.records().setSaveAction(scoreSaver::saveScore);

        view.win().setNewGameAction(e -> startNewGame());
        view.win().setExitAction(e -> view.dispose());

        view.lose().setNewGameAction(e -> startNewGame());
        view.lose().setExitAction(e -> view.dispose());
    }
}
