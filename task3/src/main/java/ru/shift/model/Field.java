package ru.shift.model;

import lombok.Getter;

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
     */
    public Cell getCell(int x, int y) {
        validateCoordinates(x, y);
        return cells[y][x];
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
     */
    private void validateCoordinates(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("Координаты клетки выходят за поля");
        }
    }
}
