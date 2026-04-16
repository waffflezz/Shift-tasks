package ru.shift.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Cell {
    private final int x;
    private final int y;
    private int adjacentMinesCount;
    private boolean opened;
    private boolean mined;
    private boolean flagged;

    public boolean open() {
        if (opened) {
            return false;
        }

        opened = true;
        return true;
    }
}
