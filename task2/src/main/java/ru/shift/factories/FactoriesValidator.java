package ru.shift.factories;

import ru.shift.exceptions.WrongParamCountException;
import ru.shift.shapes.types.ShapeType;

public class FactoriesValidator {
    public static void validateParamsCount(Object[] params, int needParamsCount, ShapeType shapeType) {
        if (params.length != needParamsCount) {
            throw new WrongParamCountException(shapeType, needParamsCount);
        }
    }
}
