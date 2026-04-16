package ru.shift.model.contracts;

public interface CellChange {
    void openCell(int x, int y);

    void openNeighboringCells(int x, int y);

    void toggleFlag(int x, int y);
}
