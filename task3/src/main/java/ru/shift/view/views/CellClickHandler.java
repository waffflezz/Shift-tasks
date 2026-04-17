package ru.shift.view.views;

import ru.shift.view.types.ButtonType;

/**
 * Обрабатывает клики по клеткам на основном игровом поле.
 */
@FunctionalInterface
public interface CellClickHandler {
    /**
     * Обрабатывает клик по клетке поля.
     *
     * @param x координата клетки по X
     * @param y координата клетки по Y
     * @param buttonType нажатая кнопка мыши
     */
    void onCellClick(int x, int y, ButtonType buttonType);
}
