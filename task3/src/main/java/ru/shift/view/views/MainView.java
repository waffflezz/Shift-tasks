package ru.shift.view.views;

import lombok.Getter;
import ru.shift.controller.Controller;
import ru.shift.dto.BombDto;
import ru.shift.dto.BombsGeneratedDto;
import ru.shift.dto.CellFlagChangedDto;
import ru.shift.dto.GameStartedDto;
import ru.shift.dto.OpenedCellDto;
import ru.shift.model.listeners.BombsGeneratedListener;
import ru.shift.model.listeners.CellFlagChangedListener;
import ru.shift.model.listeners.CellOpenListener;
import ru.shift.model.listeners.GameStartListener;
import ru.shift.view.types.ButtonType;
import ru.shift.view.types.GameImage;
import ru.shift.view.windows.MainWindow;

public class MainView implements GameStartListener, CellOpenListener, BombsGeneratedListener, CellFlagChangedListener {
    private static final boolean DEBUG_SHOW_BOMBS = false;

    private final Controller controller;
    private final MainWindow mainWindow;
    @Getter
    private final SettingsView settingsWindowView;
    @Getter
    private final HighScoresView highScoresWindowView;
    @Getter
    private final WinView winWindowView;
    @Getter
    private final LoseView loseWindowView;
    @Getter
    private final RecordsView recordsWindowView;
    private boolean[][] bombs;
    private boolean[][] flaggedCells;

    public MainView(Controller controller) {
        this.controller = controller;
        mainWindow = new MainWindow();
        settingsWindowView = new SettingsView(mainWindow.getWindow(), controller);
        highScoresWindowView = new HighScoresView(mainWindow.getWindow(), controller);
        winWindowView = new WinView(mainWindow.getWindow(), controller);
        loseWindowView = new LoseView(mainWindow.getWindow(), controller);
        recordsWindowView = new RecordsView(mainWindow.getWindow(), controller);

        bindActions();
    }

    public void setVisible(boolean visible) {
        mainWindow.setVisible(visible);
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

    private void bindActions() {
        mainWindow.setNewGameMenuAction(e -> controller.startNewGame());
        mainWindow.setHighScoresMenuAction(e -> highScoresWindowView.setVisible(true));
        mainWindow.setSettingsMenuAction(e -> settingsWindowView.setVisible(true));
        mainWindow.setCellClickHandler((x, y, buttonType) -> {
            if (buttonType == ButtonType.LEFT_BUTTON) {
                controller.openCell(x, y);
                return;
            }

            if (buttonType == ButtonType.MIDDLE_BUTTON) {
                controller.openNeighboringCells(x, y);
                return;
            }

            if (buttonType == ButtonType.RIGHT_BUTTON) {
                controller.toggleFlag(x, y);
            }
        });
    }
}
