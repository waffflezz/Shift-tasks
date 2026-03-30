package ru.shift.shapes.types;

import static ru.shift.constants.IOConstants.EOL;

public enum ShapeType {
    CIRCLE,
    RECTANGLE,
    TRIANGLE;

    public static int computeMaxLengthFigureType() {
        int maxNameLength = 0;
        for (ShapeType shapeType : values()) {
            maxNameLength = Math.max(maxNameLength, shapeType.toString().length());
        }
        return maxNameLength + EOL.length();
    }
}
