package ru.shift.shapes.types;

import ru.shift.exceptions.UnknownShapeTypeException;

public class Mapper {
    public static ShapeType fromStringToShapeType(String stringShapeType) {
        return switch (stringShapeType) {
            case "CIRCLE" -> ShapeType.CIRCLE;
            case "RECTANGLE" -> ShapeType.RECTANGLE;
            case "TRIANGLE" -> ShapeType.TRIANGLE;
            default -> throw new UnknownShapeTypeException(stringShapeType);
        };
    }
}
