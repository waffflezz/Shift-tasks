package ru.shift.model.listeners;

import ru.shift.dto.OpenedCellDto;

@FunctionalInterface
public interface CellOpenListener extends ModelListener {
    void onCellOpened(OpenedCellDto openedCell);
}
