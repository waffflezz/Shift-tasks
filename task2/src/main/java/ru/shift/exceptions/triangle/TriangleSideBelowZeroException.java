package ru.shift.exceptions.triangle;

import ru.shift.constants.Messages;

public class TriangleSideBelowZeroException extends IllegalArgumentException {
    public TriangleSideBelowZeroException() {
        super(Messages.TRIANGLE_SIDES_BELOW_ZERO_EXCEPTION);
    }
}
