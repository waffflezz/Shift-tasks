package ru.shift.model.listeners;

import ru.shift.dto.CellFlagChangedDto;

@FunctionalInterface
public interface CellFlagChangedListener extends ModelListener {
    void onCellFlagChanged(CellFlagChangedDto cellFlagChanged);
}
