package ru.shift.model.listeners;

import ru.shift.dto.OpenedCellDto;

/**
 * Получает уведомления об открытых клетках.
 */
@FunctionalInterface
public interface CellOpenListener extends ModelListener {
    /**
     * Обрабатывает событие открытия клетки.
     *
     * @param openedCell данные об открытой клетке
     */
    void onCellOpened(OpenedCellDto openedCell);
}
