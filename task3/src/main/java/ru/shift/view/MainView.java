package ru.shift.view;

import lombok.Getter;
import ru.shift.dto.BombDto;
import ru.shift.dto.BombsGeneratedDto;
import ru.shift.dto.CellFlagChangedDto;
import ru.shift.dto.GameStartedDto;
import ru.shift.dto.OpenedCellDto;
import ru.shift.dto.TimerTickDto;
import ru.shift.external.listeners.ExternalListener;
import ru.shift.external.listeners.TimerTickListener;
import ru.shift.model.listeners.BombsGeneratedListener;
import ru.shift.model.listeners.CellFlagChangedListener;
import ru.shift.model.listeners.CellOpenListener;
import ru.shift.model.listeners.GameStartListener;
import ru.shift.model.listeners.GameStateChangedListener;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observer.ObserversRegistry;
import ru.shift.view.types.GameImage;
import ru.shift.view.actions.GameResultViewActions;
import ru.shift.view.actions.MainViewActions;
import ru.shift.view.actions.RecordsViewActions;
import ru.shift.view.actions.SettingsViewActions;
import ru.shift.view.views.CellClickHandler;
import ru.shift.view.views.HighScoresView;
import ru.shift.view.views.LoseView;
import ru.shift.view.views.RecordsView;
import ru.shift.view.views.SettingsView;
import ru.shift.view.views.WinView;
import ru.shift.view.windows.MainWindow;

import java.awt.event.ActionListener;

public class MainView implements MainViewActions,
        GameStartListener, CellOpenListener, BombsGeneratedListener,
        CellFlagChangedListener, TimerTickListener {
    private static final boolean DEBUG_SHOW_BOMBS = false;

    private final MainWindow mainWindow;
    @Getter
    private final SettingsView settingsView;
    @Getter
    private final HighScoresView highScoresView;
    @Getter
    private final WinView winView;
    @Getter
    private final LoseView loseView;
    @Getter
    private final RecordsView recordsView;
    private boolean[][] bombs;
    private boolean[][] flaggedCells;

    public MainView(
            ObserversRegistry<ModelListener> modelObservers,
            ObserversRegistry<ExternalListener> externalObservers
    ) {
        mainWindow = new MainWindow();
        settingsView = new SettingsView(mainWindow.getWindow());
        highScoresView = new HighScoresView(mainWindow.getWindow(), externalObservers);
        winView = new WinView(mainWindow.getWindow());
        loseView = new LoseView(mainWindow.getWindow());
        recordsView = new RecordsView(mainWindow.getWindow(), externalObservers);

        bindObservers(modelObservers, externalObservers);
    }

    public void setVisible(boolean visible) {
        mainWindow.setVisible(visible);
    }

    @Override
    public void setHighScoresMenuAction(ActionListener listener) {
        mainWindow.setHighScoresMenuAction(listener);
    }

    @Override
    public void setSettingsMenuAction(ActionListener listener) {
        mainWindow.setSettingsMenuAction(listener);
    }

    @Override
    public void onGameStarted(GameStartedDto gameStarted) {
        bombs = new boolean[gameStarted.height()][gameStarted.width()];
        flaggedCells = new boolean[gameStarted.height()][gameStarted.width()];
        mainWindow.createGameField(gameStarted.height(), gameStarted.width());
        mainWindow.setBombsCount(gameStarted.minesCount());
        mainWindow.setTimerValue(0);
        setVisible(true);
    }

    @Override
    public void onCellOpened(OpenedCellDto openedCell) {
        mainWindow.setCellImage(openedCell.x(), openedCell.y(), resolveCellImage(openedCell));
    }

    @Override
    public void onBombsGenerated(BombsGeneratedDto bombsGenerated) {
        for (BombDto bomb : bombsGenerated.bombs()) {
            bombs[bomb.y()][bomb.x()] = true;
        }

        showBombs(bombsGenerated);
    }

    @Override
    public void onCellFlagChanged(CellFlagChangedDto cellFlagChanged) {
        flaggedCells[cellFlagChanged.y()][cellFlagChanged.x()] = cellFlagChanged.flagged();
        mainWindow.setBombsCount(cellFlagChanged.remainingMinesCount());

        if (cellFlagChanged.flagged()) {
            mainWindow.setCellImage(cellFlagChanged.x(), cellFlagChanged.y(), GameImage.MARKED);
            return;
        }

        if (DEBUG_SHOW_BOMBS && bombs[cellFlagChanged.y()][cellFlagChanged.x()]) {
            mainWindow.setCellImage(cellFlagChanged.x(), cellFlagChanged.y(), GameImage.BOMB);
            return;
        }

        mainWindow.setCellImage(cellFlagChanged.x(), cellFlagChanged.y(), GameImage.CLOSED);
    }

    @Override
    public void onTimerTick(TimerTickDto timerTick) {
        mainWindow.setTimerValue(timerTick.elapsedSeconds());
    }

    @Override
    public void setNewGameMenuAction(ActionListener listener) {
        mainWindow.setNewGameMenuAction(listener);
    }

    @Override
    public void setCellClickAction(CellClickHandler handler) {
        mainWindow.setCellClickHandler(handler);
    }

    @Override
    public void showHighScores() {
        highScoresView.setVisible(true);
    }

    @Override
    public void showSettings() {
        settingsView.setVisible(true);
    }

    @Override
    public void dispose() {
        mainWindow.dispose();
    }

    @Override
    public SettingsViewActions settings() {
        return settingsView;
    }

    @Override
    public GameResultViewActions win() {
        return winView;
    }

    @Override
    public GameResultViewActions lose() {
        return loseView;
    }

    @Override
    public RecordsViewActions records() {
        return recordsView;
    }

    private GameImage resolveCellImage(OpenedCellDto openedCell) {
        if (openedCell.mined()) {
            return GameImage.BOMB;
        }

        return switch (openedCell.adjacentMinesCount()) {
            case 0 -> GameImage.EMPTY;
            case 1 -> GameImage.NUM_1;
            case 2 -> GameImage.NUM_2;
            case 3 -> GameImage.NUM_3;
            case 4 -> GameImage.NUM_4;
            case 5 -> GameImage.NUM_5;
            case 6 -> GameImage.NUM_6;
            case 7 -> GameImage.NUM_7;
            case 8 -> GameImage.NUM_8;
            default -> throw new IllegalArgumentException(
                    "Unsupported adjacent mines count: " + openedCell.adjacentMinesCount()
            );
        };
    }

    private void showBombs(BombsGeneratedDto bombsGenerated) {
        if (!DEBUG_SHOW_BOMBS) {
            return;
        }

        for (BombDto bomb : bombsGenerated.bombs()) {
            if (flaggedCells[bomb.y()][bomb.x()]) {
                continue;
            }

            mainWindow.setCellImage(bomb.x(), bomb.y(), GameImage.BOMB);
        }
    }

    private void bindObservers(
            ObserversRegistry<ModelListener> modelObservers,
            ObserversRegistry<ExternalListener> externalObservers
    ) {
        modelObservers.addListener(GameStartListener.class, this);
        modelObservers.addListener(CellOpenListener.class, this);
        modelObservers.addListener(BombsGeneratedListener.class, this);
        modelObservers.addListener(CellFlagChangedListener.class, this);
        modelObservers.addListener(GameStateChangedListener.class, this.getLoseView());
        modelObservers.addListener(GameStateChangedListener.class, this.getWinView());
        externalObservers.addListener(TimerTickListener.class, this);
    }
}
