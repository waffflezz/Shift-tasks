package ru.shift.model;

import ru.shift.dto.BombDto;
import ru.shift.model.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Отвечает за расстановку мин на игровом поле.
 */
class MinePlacer {
    /**
     * Расставляет мины на поле, исключая указанную клетку.
     *
     * @param field игровое поле
     * @param minesCount количество мин для расстановки
     * @param excludedX координата клетки по X, в которую нельзя ставить мину
     * @param excludedY координата клетки по Y, в которую нельзя ставить мину
     * @return список координат расставленных мин
     */
    List<BombDto> placeMines(Field field, int minesCount, int excludedX, int excludedY) {
        int[] availableCellIndexes = createAvailableCellIndexes(
                field.getWidth(),
                field.getHeight(),
                excludedX,
                excludedY
        );

        return placeMines(field, minesCount, availableCellIndexes);
    }

    /**
     * Выбирает случайные доступные клетки, помечает их как заминированные и возвращает их координаты.
     *
     * @param field игровое поле
     * @param minesCount количество мин для размещения
     * @param availableCellIndexes индексы клеток, доступных для размещения мин
     * @return список координат размещённых мин
     */
    private List<BombDto> placeMines(Field field, int minesCount, int[] availableCellIndexes) {
        List<BombDto> bombs = new ArrayList<>(minesCount);

        for (int mineIndex = 0; mineIndex < minesCount; mineIndex++) {
            int selectedIndex = ThreadLocalRandom.current().nextInt(mineIndex, availableCellIndexes.length);
            swap(availableCellIndexes, mineIndex, selectedIndex);

            int cellIndex = availableCellIndexes[mineIndex];
            int x = toX(field.getWidth(), cellIndex);
            int y = toY(field.getWidth(), cellIndex);

            field.getCell(x, y).setMined(true);
            bombs.add(new BombDto(x, y));
        }

        return bombs;
    }

    /**
     * Создаёт массив индексов клеток, доступных для размещения мин.
     *
     * @param width ширина поля
     * @param height высота поля
     * @param excludedX координата исключённой клетки по X
     * @param excludedY координата исключённой клетки по Y
     * @return индексы клеток, в которые можно поставить мину
     */
    private int[] createAvailableCellIndexes(int width, int height, int excludedX, int excludedY) {
        int excludedIndex = toCellIndex(width, excludedX, excludedY);
        int availableCellsCount = width * height - 1;
        int[] availableCellIndexes = new int[availableCellsCount];
        int nextAvailableIndex = 0;

        for (int cellIndex = 0; cellIndex < width * height; cellIndex++) {
            if (cellIndex == excludedIndex) {
                continue;
            }

            availableCellIndexes[nextAvailableIndex++] = cellIndex;
        }

        return availableCellIndexes;
    }

    /**
     * Преобразует координаты клетки в линейный индекс.
     *
     * @param width ширина поля
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @return линейный индекс клетки
     */
    private int toCellIndex(int width, int x, int y) {
        return y * width + x;
    }

    /**
     * Возвращает координату X по линейному индексу клетки.
     *
     * @param width ширина поля
     * @param cellIndex линейный индекс клетки
     * @return координата клетки по X
     */
    private int toX(int width, int cellIndex) {
        return cellIndex % width;
    }

    /**
     * Возвращает координату Y по линейному индексу клетки.
     *
     * @param width ширина поля
     * @param cellIndex линейный индекс клетки
     * @return координата клетки по Y
     */
    private int toY(int width, int cellIndex) {
        return cellIndex / width;
    }

    /**
     * Меняет местами два элемента массива.
     *
     * @param values исходный массив
     * @param firstIndex индекс первого элемента
     * @param secondIndex индекс второго элемента
     */
    private void swap(int[] values, int firstIndex, int secondIndex) {
        int temp = values[firstIndex];
        values[firstIndex] = values[secondIndex];
        values[secondIndex] = temp;
    }
}
