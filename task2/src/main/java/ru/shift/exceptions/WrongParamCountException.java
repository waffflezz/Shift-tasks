package ru.shift.exceptions;

import ru.shift.constants.Messages;
import ru.shift.shapes.types.ShapeType;

public class WrongParamCountException extends IllegalArgumentException {
    public WrongParamCountException(ShapeType shapeType, int needParams) {
        super(Messages.WRONG_COUNT_OF_PARAM_EXCEPTION.formatted(shapeType, needParams));
    }
}
