package ru.shift.exceptions.triangle;

import ru.shift.constants.Messages;

public class TriangleCantExistsException extends IllegalArgumentException {
    public TriangleCantExistsException(double sideA, double sideB, double sideC) {
        super(Messages.TRIANGLE_CANT_EXISTS_EXCEPTION.formatted(sideA, sideB, sideC));
    }
}
