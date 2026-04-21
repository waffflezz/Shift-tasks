package ru.shift.model.field;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Представляет одну клетку игрового поля.
 */
@Getter
@RequiredArgsConstructor
public class Cell {
    private final static int TOGGLE_FLAG = 1;
    private final static int REMOVE_FLAG = -1;

    private final int x;
    private final int y;

    @Setter
    private int adjacentMinesCount;

    @Setter
    private boolean mined;

    private boolean opened;
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

    /**
     * Переключает флаг на клетке.
     *
     * @return {@link Cell#TOGGLE_FLAG}, если флаг установлен, или {@link Cell#REMOVE_FLAG}, если флаг снят
     */
    public int toggleFlag() {
        flagged = !flagged;
        return flagged ? TOGGLE_FLAG : REMOVE_FLAG;
    }

    public void removeFlag() {
        flagged = false;
    }
}
