package ru.shift.model;

import lombok.extern.slf4j.Slf4j;
import ru.shift.GameLevel;
import ru.shift.dto.BombDto;
import ru.shift.model.field.Cell;
import ru.shift.model.field.Field;
import ru.shift.model.listeners.ModelListener;
import ru.shift.observers.ObserversRegistry;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Основная игровая модель, управляющая состоянием поля и уведомляющая слушателей.
 */
@Slf4j
public class MainModel implements GameModel {
    private final Notifier notifier;
    private final MinePlacer minePlacer = new MinePlacer();

    private int minesCount;

    private Field field;
    private int safeCellsCount;
    private int openedSafeCellsCount;
    private int flagsCount;
    private GameState gameState;

    /**
     * Создаёт игровую модель с указанными настройками поля.
     *
     * @param width ширина поля
     * @param height высота поля
     * @param minesCount количество мин на поле
     * @param observers реестр слушателей модели
     */
    public MainModel(int width, int height, int minesCount, ObserversRegistry<ModelListener> observers) {
        validateMinesCount(width, height, minesCount);
        this.notifier = new Notifier(observers);
        applyGameSettings(width, height, minesCount);
    }

    @Override
    public void startNewGame() {
        log.info("Start new game");

        resetGameState();
        notifier.notifyGameStarted(field.getWidth(), field.getHeight(), minesCount);
        notifier.notifyGameStateChanged(gameState);
    }

    @Override
    public void startNewGame(GameLevel gameLevel) {
        log.info("Start new game with level {}", gameLevel.name());

        applyGameSettings(gameLevel.getWidth(), gameLevel.getHeight(), gameLevel.getMinesCount());
        resetGameState();
        notifier.notifyGameStarted(field.getWidth(), field.getHeight(), minesCount);
        notifier.notifyGameStateChanged(gameState);
    }

    @Override
    public void openCell(int x, int y) {
        log.debug("Try to open cell in cords X: {}, Y: {}", x, y);

        if (isGameFinished()) {
            return;
        }

        Cell cell = field.getCell(x, y);

        if (cell.isOpened() || cell.isFlagged()) {
            return;
        }

        if (gameState == GameState.NEW) {
            fillFieldWithMines(x, y);
            field.calculateAdjacentMinesCounts();
            gameState = GameState.IN_PROGRESS;
            notifier.notifyGameStateChanged(gameState);
        }

        if (cell.isMined()) {
            openCellAndNotify(cell);
            lostGameAndNotify();
            return;
        }

        openSafeCell(cell);
        wonGameAndNotify();
    }

    @Override
    public void openNeighboringCells(int x, int y) {
        log.debug("Try to open neighboring cells in cords X: {}, Y: {}", x, y);

        if (isGameFinished()) {
            return;
        }

        Cell cell = field.getCell(x, y);

        if (!CellOpeningRules.canOpenNeighboringCells(cell)) {
            return;
        }

        if (!CellOpeningRules.hasRequiredNeighboringFlags(field, cell)) {
            return;
        }

        if (openClosedNeighboringCells(x, y)) {
            lostGameAndNotify();
            return;
        }

        wonGameAndNotify();
    }

    @Override
    public void toggleFlag(int x, int y) {
        log.debug("Try to toggle flag in cords X: {}, Y: {}", x, y);

        if (isGameFinished()) {
            return;
        }

        Cell cell = field.getCell(x, y);

        if (cell.isOpened()) {
            return;
        }

        flagsCount += cell.toggleFlag();

        notifier.notifyCellFlagChanged(x, y, cell.isFlagged(), minesCount - flagsCount);
    }

    /**
     * Открывает безопасную клетку.
     * Если рядом с клеткой есть мины, открывает только эту клетку.
     * Если рядом мин нет, открывает связанную пустую область.
     *
     * @param cell безопасная клетка для открытия
     */
    private void openSafeCell(Cell cell) {
        if (cell.getAdjacentMinesCount() > 0) {
            openCellAndNotify(cell);
            return;
        }

        openEmptyArea(cell.getX(), cell.getY());
    }

    /**
     * Открывает закрытые соседние клетки вокруг указанной клетки.
     *
     * @param x координата исходной клетки по X
     * @param y координата исходной клетки по Y
     * @return {@code true}, если среди открытых соседей была мина, иначе {@code false}
     */
    private boolean openClosedNeighboringCells(int x, int y) {
        for (Cell neighbor : field.getNeighboringCells(x, y)) {
            if (neighbor.isOpened() || neighbor.isFlagged()) {
                continue;
            }

            if (neighbor.isMined()) {
                openCellAndNotify(neighbor);
                return true;
            }

            openSafeCell(neighbor);
        }

        return false;
    }

    /**
     * Случайным образом расставляет мины на поле, исключая первую открытую клетку.
     *
     * @param excludedX координата исключённой клетки по X
     * @param excludedY координата исключённой клетки по Y
     */
    private void fillFieldWithMines(int excludedX, int excludedY) {
        log.debug("Generate {} bombs excluding cords X: {}, Y: {}", minesCount, excludedX, excludedY);

        List<BombDto> bombs = minePlacer.placeMines(field, minesCount, excludedX, excludedY);

        notifier.notifyBombsGenerated(bombs);
    }

    /**
     * Открывает связанную пустую область с помощью обхода в ширину.
     *
     * @param startX начальная координата клетки по X
     * @param startY начальная координата клетки по Y
     */
    private void openEmptyArea(int startX, int startY) {
        log.debug("Start opening empty area from cords X: {}, Y: {}", startX, startY);

        ArrayDeque<Cell> cellsToOpen = new ArrayDeque<>();
        Set<Cell> visitedCells = new HashSet<>();

        Cell startCell = field.getCell(startX, startY);

        cellsToOpen.add(startCell);
        visitedCells.add(startCell);

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

            addNeighboringCells(cellsToOpen, visitedCells, cell.getX(), cell.getY());
        }
    }

    /**
     * Добавляет соседние клетки-кандидаты для дальнейшей обработки пустой области.
     *
     * @param cellsToOpen очередь клеток на обработку
     * @param x координата исходной клетки по X
     * @param y координата исходной клетки по Y
     */
    private void addNeighboringCells(ArrayDeque<Cell> cellsToOpen, Set<Cell> visited, int x, int y) {
        for (Cell neighbor : field.getNeighboringCells(x, y)) {
            if (neighbor.isOpened() || neighbor.isMined() || visited.contains(neighbor)) {
                continue;
            }

            cellsToOpen.addLast(neighbor);
            visited.add(neighbor);
        }
    }

    /**
     * Снимает флаг с клетки перед её открытием и уведомляет слушателей.
     *
     * @param cell клетка для обновления
     */
    private void removeFlagIfNeeded(Cell cell) {
        if (!cell.isFlagged()) {
            return;
        }

        cell.removeFlag();
        flagsCount--;

        notifier.notifyCellFlagChanged(cell.getX(), cell.getY(), false, minesCount - flagsCount);
    }

    /**
     * Открывает клетку и уведомляет слушателей, если её состояние изменилось.
     *
     * @param cell клетка для открытия
     */
    private void openCellAndNotify(Cell cell) {
        if (!cell.open()) {
            return;
        }

        if (!cell.isMined()) {
            openedSafeCellsCount++;
        }

        notifier.notifyCellOpened(cell);
    }

    /**
     * Переводит игру в состояние проигрыша и уведомляет слушателей.
     */
    private void lostGameAndNotify() {
        if (isGameFinished()) {
            return;
        }

        gameState = GameState.LOST;
        log.info("Game lost");
        notifier.notifyGameStateChanged(gameState);
    }

    /**
     * Переводит игру в состояние победы, когда открыты все безопасные клетки.
     */
    private void wonGameAndNotify() {
        if (isGameFinished() || !hasWon()) {
            return;
        }

        gameState = GameState.WON;
        log.info("Game won");
        notifier.notifyGameStateChanged(gameState);
    }

    /**
     * Применяет к модели размеры поля и количество мин.
     *
     * @param width ширина поля
     * @param height высота поля
     * @param minesCount количество мин на поле
     */
    private void applyGameSettings(int width, int height, int minesCount) {
        validateMinesCount(width, height, minesCount);
        this.minesCount = minesCount;
        this.safeCellsCount = width * height - minesCount;
        this.field = new Field(width, height);
        log.debug(
                "Apply game settings. Width: {}, height: {}, mines: {}, safe cells: {}",
                width,
                height,
                minesCount,
                safeCellsCount
        );
    }

    /**
     * Сбрасывает всё изменяемое состояние для новой игры.
     */
    private void resetGameState() {
        field = new Field(field.getWidth(), field.getHeight());
        openedSafeCellsCount = 0;
        flagsCount = 0;
        gameState = GameState.NEW;
        log.debug("Reset game state for field {}x{} with {} mines", field.getWidth(), field.getHeight(), minesCount);
    }

    /**
     * Проверяет, открыты ли все безопасные клетки.
     *
     * @return {@code true}, если игрок победил
     */
    private boolean hasWon() {
        return openedSafeCellsCount == safeCellsCount;
    }

    /**
     * Проверяет, завершилась ли уже игра.
     *
     * @return {@code true}, если игра выиграна или проиграна
     */
    private boolean isGameFinished() {
        return gameState == GameState.WON || gameState == GameState.LOST;
    }

    /**
     * Проверяет, можно ли разместить запрошенное количество мин на поле.
     *
     * @param width ширина поля
     * @param height высота поля
     * @param minesCount количество мин
     */
    private void validateMinesCount(int width, int height, int minesCount) {
        if (minesCount < 0) {
            throw new IllegalArgumentException("Количество мин не может быть отрицательным");
        }

        if (minesCount >= width * height) {
            throw new IllegalArgumentException("Должна оставаться как минимум одно безопасное поле");
        }
    }
}
