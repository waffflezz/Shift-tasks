package ru.shift.model.contracts;

/**
 * Описывает операции, изменяющие состояние клеток.
 */
public interface CellChange {
    /**
     * Открывает клетку по её координатам.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     */
    void openCell(int x, int y);

    /**
     * Открывает соседние клетки вокруг указанной клетки.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     */
    void openNeighboringCells(int x, int y);

    /**
     * Переключает флаг на указанной клетке.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     */
    void toggleFlag(int x, int y);
}
