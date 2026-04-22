package ru.shift.model.field;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранит размеры игрового поля и все его клетки.
 */
@Getter
public class Field {
    private final int width;
    private final int height;
    private final Cell[][] cells;

    /**
     * Создаёт поле указанного размера.
     *
     * @param width ширина поля
     * @param height высота поля
     * @throws IllegalArgumentException если ширина или высота неположительные
     */
    public Field(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Поля должны быть положительными");
        }

        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        initializeCells();
    }

    /**
     * Возвращает клетку по её координатам.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @return запрошенная клетка
     * @throws IndexOutOfBoundsException если координаты выходят за границы поля
     */
    public Cell getCell(int x, int y) {
        validateCoordinates(x, y);
        return cells[y][x];
    }

    /**
     * Возвращает все корректные соседние клетки вокруг указанных координат.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @return соседние клетки
     */
    public List<Cell> getNeighboringCells(int x, int y) {
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

                neighboringCells.add(getCell(neighborX, neighborY));
            }
        }

        return neighboringCells;
    }

    /**
     * Подсчитывает количество флагов вокруг указанной клетки.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @return количество соседних помеченных клеток
     */
    public int countNeighboringFlags(int x, int y) {
        int neighboringFlagsCount = 0;

        for (Cell neighbor : getNeighboringCells(x, y)) {
            if (neighbor.isFlagged()) {
                neighboringFlagsCount++;
            }
        }

        return neighboringFlagsCount;
    }

    /**
     * Вычисляет количество соседних мин для каждой клетки.
     */
    public void calculateAdjacentMinesCounts() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getCell(x, y).setAdjacentMinesCount(countAdjacentMines(x, y));
            }
        }
    }

    /**
     * Подсчитывает количество мин вокруг указанной клетки.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @return количество соседних заминированных клеток
     */
    private int countAdjacentMines(int x, int y) {
        int adjacentMinesCount = 0;

        for (Cell neighbor : getNeighboringCells(x, y)) {
            if (neighbor.isMined()) {
                adjacentMinesCount++;
            }
        }

        return adjacentMinesCount;
    }

    /**
     * Инициализирует все клетки поля.
     */
    private void initializeCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }

    /**
     * Проверяет, что указанные координаты принадлежат полю.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @throws IndexOutOfBoundsException если координаты выходят за границы поля
     */
    private void validateCoordinates(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Координаты клетки выходят за поля");
        }
    }

    /**
     * Проверяет, принадлежат ли координаты текущему полю.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @return {@code true}, если координаты корректны
     */
    private boolean isInsideField(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
