package ru.shift.model;

import ru.shift.GameLevel;
import ru.shift.dto.BombDto;
import ru.shift.dto.BombsGeneratedDto;
import ru.shift.dto.CellFlagChangedDto;
import ru.shift.dto.GameStartedDto;
import ru.shift.dto.OpenedCellDto;
import ru.shift.model.listeners.BombsGeneratedListener;
import ru.shift.model.listeners.CellFlagChangedListener;
import ru.shift.model.listeners.CellOpenListener;
import ru.shift.model.listeners.GameLostListener;
import ru.shift.model.listeners.GameStartListener;
import ru.shift.model.listeners.GameWonListener;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observer.ObserversRegistry;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainModel implements GameModel {
    private final ObserversRegistry<ModelListener> observers;

    private int width;
    private int height;
    private int minesCount;

    private Field field;
    private int safeCellsCount;
    private int openedSafeCellsCount;
    private int flagsCount;
    private GameState gameState;

    public MainModel(int width, int height, int minesCount, ObserversRegistry<ModelListener> observers) {
        validateMinesCount(width, height, minesCount);
        this.observers = observers;
        applyGameSettings(width, height, minesCount);
    }

    @Override
    public void startNewGame() {
        resetGameState();
        notifyGameStarted();
    }

    @Override
    public void startNewGame(GameLevel gameLevel) {
        applyGameSettings(gameLevel.getWidth(), gameLevel.getHeight(), gameLevel.getMinesCount());
        resetGameState();
        notifyGameStarted();
    }

    @Override
    public void openCell(int x, int y) {
        if (isGameFinished()) {
            return;
        }

        Cell cell = field.getCell(x, y);

        if (cell.isOpened() || cell.isFlagged()) {
            return;
        }

        if (gameState == GameState.NEW) {
            fillFieldWithMines(x, y);
            calculateAdjacentMinesCounts();
            gameState = GameState.IN_PROGRESS;
        }

        if (cell.isMined()) {
            openCellAndNotify(cell);
            notifyGameLost();
            return;
        }

        if (cell.getAdjacentMinesCount() > 0) {
            openCellAndNotify(cell);
            notifyGameWonIfNeeded();
            return;
        }

        openEmptyArea(x, y);
        notifyGameWonIfNeeded();
    }

    @Override
    public void openNeighboringCells(int x, int y) {
        if (isGameFinished()) {
            return;
        }

        Cell cell = field.getCell(x, y);

        if (!cell.isOpened() || cell.isMined() || cell.getAdjacentMinesCount() <= 0) {
            return;
        }

        if (countNeighboringFlags(x, y) != cell.getAdjacentMinesCount()) {
            return;
        }

        boolean mineOpened = false;

        for (Cell neighbor : getNeighboringCells(x, y)) {
            if (neighbor.isOpened() || neighbor.isFlagged()) {
                continue;
            }

            if (neighbor.isMined()) {
                openCellAndNotify(neighbor);
                mineOpened = true;
                continue;
            }

            if (neighbor.getAdjacentMinesCount() > 0) {
                openCellAndNotify(neighbor);
                continue;
            }

            openEmptyArea(neighbor.getX(), neighbor.getY());
        }

        if (mineOpened) {
            notifyGameLost();
            return;
        }

        notifyGameWonIfNeeded();
    }

    @Override
    public void toggleFlag(int x, int y) {
        if (isGameFinished()) {
            return;
        }

        Cell cell = field.getCell(x, y);

        if (cell.isOpened()) {
            return;
        }

        boolean flagged = !cell.isFlagged();
        cell.setFlagged(flagged);

        if (flagged) {
            flagsCount++;
        } else {
            flagsCount--;
        }

        CellFlagChangedDto cellFlagChanged = new CellFlagChangedDto(
                x,
                y,
                flagged,
                minesCount - flagsCount
        );
        observers.notifyListeners(CellFlagChangedListener.class, listener -> listener.onCellFlagChanged(cellFlagChanged));
    }

    private void fillFieldWithMines(int excludedX, int excludedY) {
        int excludedIndex = excludedY * width + excludedX;
        int availableCellsCount = width * height - 1;
        int[] availableCellIndexes = new int[availableCellsCount];
        int nextAvailableIndex = 0;
        List<BombDto> bombs = new ArrayList<>(minesCount);

        for (int cellIndex = 0; cellIndex < width * height; cellIndex++) {
            if (cellIndex == excludedIndex) {
                continue;
            }

            availableCellIndexes[nextAvailableIndex++] = cellIndex;
        }

        for (int mineIndex = 0; mineIndex < minesCount; mineIndex++) {
            int selectedIndex = ThreadLocalRandom.current().nextInt(mineIndex, availableCellsCount);
            swap(availableCellIndexes, mineIndex, selectedIndex);

            int cellIndex = availableCellIndexes[mineIndex];
            int x = cellIndex % width;
            int y = cellIndex / width;
            field.getCell(x, y).setMined(true);
            bombs.add(new BombDto(x, y));
        }

        BombsGeneratedDto bombsGenerated = new BombsGeneratedDto(List.copyOf(bombs));
        observers.notifyListeners(BombsGeneratedListener.class, listener -> listener.onBombsGenerated(bombsGenerated));
    }

    private void calculateAdjacentMinesCounts() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                field.getCell(x, y).setAdjacentMinesCount(countAdjacentMines(x, y));
            }
        }
    }

    private int countAdjacentMines(int x, int y) {
        int adjacentMinesCount = 0;

        for (Cell neighbor : getNeighboringCells(x, y)) {
            if (neighbor.isMined()) {
                adjacentMinesCount++;
            }
        }

        return adjacentMinesCount;
    }

    private int countNeighboringFlags(int x, int y) {
        int neighboringFlagsCount = 0;

        for (Cell neighbor : getNeighboringCells(x, y)) {
            if (neighbor.isFlagged()) {
                neighboringFlagsCount++;
            }
        }

        return neighboringFlagsCount;
    }

    private void openEmptyArea(int startX, int startY) {
        ArrayDeque<Cell> cellsToOpen = new ArrayDeque<>();
        cellsToOpen.add(field.getCell(startX, startY));

        while (!cellsToOpen.isEmpty()) {
            Cell cell = cellsToOpen.removeFirst();

            if (cell.isOpened() || cell.isMined()) {
                continue;
            }

            removeFlagIfNeeded(cell);
            openCellAndNotify(cell);

            if (cell.getAdjacentMinesCount() > 0) {
                continue;
            }

            addNeighboringCells(cellsToOpen, cell.getX(), cell.getY());
        }
    }

    private void addNeighboringCells(ArrayDeque<Cell> cellsToOpen, int x, int y) {
        for (Cell neighbor : getNeighboringCells(x, y)) {
            if (neighbor.isOpened() || neighbor.isMined()) {
                continue;
            }

            cellsToOpen.addLast(neighbor);
        }
    }

    private List<Cell> getNeighboringCells(int x, int y) {
        final int cellsAround = 8;
        List<Cell> neighboringCells = new ArrayList<>(cellsAround);

        for (int offsetY = -1; offsetY <= 1; offsetY++) {
            for (int offsetX = -1; offsetX <= 1; offsetX++) {
                if (offsetX == 0 && offsetY == 0) {
                    continue;
                }

                int neighborX = x + offsetX;
                int neighborY = y + offsetY;

                if (!isInsideField(neighborX, neighborY)) {
                    continue;
                }

                neighboringCells.add(field.getCell(neighborX, neighborY));
            }
        }

        return neighboringCells;
    }

    private void removeFlagIfNeeded(Cell cell) {
        if (!cell.isFlagged()) {
            return;
        }

        cell.setFlagged(false);
        flagsCount--;

        CellFlagChangedDto cellFlagChanged = new CellFlagChangedDto(
                cell.getX(),
                cell.getY(),
                false,
                minesCount - flagsCount
        );
        observers.notifyListeners(CellFlagChangedListener.class, listener -> listener.onCellFlagChanged(cellFlagChanged));
    }

    private void openCellAndNotify(Cell cell) {
        if (!cell.open()) {
            return;
        }

        if (!cell.isMined()) {
            openedSafeCellsCount++;
        }

        notifyCellOpened(cell);
    }

    private void notifyCellOpened(Cell cell) {
        OpenedCellDto openedCell = new OpenedCellDto(
                cell.getX(),
                cell.getY(),
                cell.isMined(),
                cell.getAdjacentMinesCount()
        );
        observers.notifyListeners(CellOpenListener.class, listener -> listener.onCellOpened(openedCell));
    }

    private void notifyGameLost() {
        if (isGameFinished()) {
            return;
        }

        gameState = GameState.LOST;
        observers.notifyListeners(GameLostListener.class, GameLostListener::onGameLost);
    }

    private void notifyGameWonIfNeeded() {
        if (isGameFinished() || !hasWon()) {
            return;
        }

        gameState = GameState.WON;
        observers.notifyListeners(GameWonListener.class, GameWonListener::onGameWon);
    }

    private void applyGameSettings(int width, int height, int minesCount) {
        validateMinesCount(width, height, minesCount);
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        this.safeCellsCount = width * height - minesCount;
        this.field = new Field(width, height);
    }

    private void resetGameState() {
        field = new Field(width, height);
        openedSafeCellsCount = 0;
        flagsCount = 0;
        gameState = GameState.NEW;
    }

    private void notifyGameStarted() {
        GameStartedDto gameStarted = new GameStartedDto(width, height, minesCount);
        observers.notifyListeners(GameStartListener.class, listener -> listener.onGameStarted(gameStarted));
    }

    private boolean isInsideField(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean hasWon() {
        return openedSafeCellsCount == safeCellsCount;
    }

    private boolean isGameFinished() {
        return gameState == GameState.WON || gameState == GameState.LOST;
    }

    private void swap(int[] values, int firstIndex, int secondIndex) {
        int temp = values[firstIndex];
        values[firstIndex] = values[secondIndex];
        values[secondIndex] = temp;
    }

    private void validateMinesCount(int width, int height, int minesCount) {
        if (minesCount < 0) {
            throw new IllegalArgumentException("Количество мин не может быть отрицательным");
        }

        if (minesCount >= width * height) {
            throw new IllegalArgumentException("Должна оставаться как минимум одно безопасное поле");
        }
    }
}
