package ru.shift.exceptions.rectangle;

import ru.shift.constants.Messages;

public class RectangleSidesBelowZeroException extends IllegalArgumentException {
    public RectangleSidesBelowZeroException() {
        super(Messages.RECTANGLE_SIDES_BELOW_ZERO_EXCEPTION);
    }
}
