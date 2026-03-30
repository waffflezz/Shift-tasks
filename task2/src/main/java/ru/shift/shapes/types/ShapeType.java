package ru.shift.shapes.types;

import ru.shift.constants.IOConstants;

public enum ShapeType {
    CIRCLE,
    RECTANGLE,
    TRIANGLE;

    /**
     * Вычисляет максимальную длину строкового представления типа фигуры
     * с учётом символа(ов) конца строки.
     *
     * @return максимальная длина имени типа фигуры + длина {@link IOConstants#EOL}
     */
    public static int computeMaxLengthFigureType() {
        int maxNameLength = 0;
        for (ShapeType shapeType : values()) {
            maxNameLength = Math.max(maxNameLength, shapeType.toString().length());
        }
        return maxNameLength + IOConstants.EOL.length();
    }
}
