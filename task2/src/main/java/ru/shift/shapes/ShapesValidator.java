package ru.shift.shapes;

import ru.shift.exceptions.circle.CircleRadiusBelowZeroException;
import ru.shift.exceptions.triangle.TriangleCantExistsException;
import ru.shift.exceptions.triangle.TriangleSideBelowZeroException;
import ru.shift.exceptions.rectangle.RectangleSidesBelowZeroException;

public class ShapesValidator {
    public static void validateTriangleCreation(double sideA, double sideB, double sideC) {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new TriangleSideBelowZeroException();
        }

        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new TriangleCantExistsException(sideA, sideB, sideC);
        }
    }

    public static void validateCircleRadius(double radius) {
        if (radius <= 0) {
            throw new CircleRadiusBelowZeroException();
        }
    }

    public static void validateRectangleSides(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new RectangleSidesBelowZeroException();
        }
    }
}
