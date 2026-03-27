package ru.shift.exceptions;

import ru.shift.constants.Messages;

public class UnknownShapeTypeException extends IllegalArgumentException {
    public UnknownShapeTypeException(String unknownShapeType) {
        super(Messages.UNKNOWN_SHAPE_TYPE.formatted(unknownShapeType));
    }
}
