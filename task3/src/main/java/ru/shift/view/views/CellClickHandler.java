package ru.shift.view.views;

import ru.shift.view.types.ButtonType;

@FunctionalInterface
public interface CellClickHandler {
    void onCellClick(int x, int y, ButtonType buttonType);
}
