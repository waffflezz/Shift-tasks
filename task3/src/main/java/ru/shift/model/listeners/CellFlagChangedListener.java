package ru.shift.model.listeners;

import ru.shift.dto.CellFlagChangedDto;

/**
 * Получает уведомления об изменении состояния флага.
 */
@FunctionalInterface
public interface CellFlagChangedListener extends ModelListener {
    /**
     * Обрабатывает изменение состояния флага.
     *
     * @param cellFlagChanged данные об изменении флага
     */
    void onCellFlagChanged(CellFlagChangedDto cellFlagChanged);
}
