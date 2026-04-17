package ru.shift.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Представляет одну клетку игрового поля.
 */
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

    /**
     * Открывает клетку, если она ещё не была открыта.
     *
     * @return {@code true}, если состояние клетки изменилось, иначе {@code false}
     */
    public boolean open() {
        if (opened) {
            return false;
        }

        opened = true;
        return true;
    }
}
