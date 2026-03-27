package ru.shift.exceptions.circle;

import ru.shift.constants.Messages;

public class CircleRadiusBelowZeroException extends IllegalArgumentException {
    public CircleRadiusBelowZeroException() {
        super(Messages.CIRCLE_RADIUS_BELOW_ZERO_EXCEPTION);
    }
}
