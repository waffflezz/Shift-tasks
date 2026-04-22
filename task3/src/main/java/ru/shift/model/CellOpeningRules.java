package ru.shift.model;

import ru.shift.model.field.Cell;
import ru.shift.model.field.Field;

/**
 * Содержит правила, определяющие возможность открытия клеток.
 */
class CellOpeningRules {
    /**
     * Проверяет, можно ли открыть соседние клетки вокруг указанной клетки.
     *
     * @param cell клетка, вокруг которой требуется открыть соседей
     * @return {@code true}, если клетка открыта, не заминирована и имеет соседние мины
     */
    static boolean canOpenNeighboringCells(Cell cell) {
        return cell.isOpened()
                && !cell.isMined()
                && cell.getAdjacentMinesCount() > 0;
    }

    /**
     * Проверяет, совпадает ли количество флагов вокруг клетки с количеством соседних мин.
     *
     * @param field игровое поле
     * @param cell клетка, для которой проверяется количество флагов вокруг
     * @return {@code true}, если количество флагов вокруг клетки совпадает с её числом соседних мин
     */
    static boolean hasRequiredNeighboringFlags(Field field, Cell cell) {
        return field.countNeighboringFlags(cell.getX(), cell.getY()) == cell.getAdjacentMinesCount();
    }
}
