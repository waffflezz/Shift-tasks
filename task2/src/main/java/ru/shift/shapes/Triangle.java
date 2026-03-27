package ru.shift.shapes;

import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.types.ShapeType;

import static ru.shift.constants.IOConstants.EOL;

public class Triangle extends Shape {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        Validator.validateTriangleCreation(sideA, sideB, sideC);

        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double getSideA() {
        return sideA;
    }

    public double getSideB() {
        return sideB;
    }

    public double getSideC() {
        return sideC;
    }

    public double computeAngleOppositeA() {
        return Math.toDegrees(
                Math.acos((sideB * sideB + sideC * sideC - sideA * sideA) / (2 * sideB * sideC))
        );
    }

    public double computeAngleOppositeB() {
        return Math.toDegrees(
                Math.acos((sideA * sideA + sideC * sideC - sideB * sideB) / (2 * sideA * sideC))
        );
    }

    public double computeAngleOppositeC() {
        return Math.toDegrees(
                Math.acos((sideA * sideA + sideB * sideB - sideC * sideC) / (2 * sideA * sideB))
        );
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.TRIANGLE;
    }

    @Override
    public double computeArea() {
        double s = computePerimeter() / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double computePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public StringBuilder getSpecifiedData() {
        StringBuilder builder = new StringBuilder();

        builder.append(Messages.TRIANGLE_SIDE_A).append(sideA).append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE).append(computeAngleOppositeA())
                .append(ShapeConstants.DEGREES)
                .append(EOL);

        builder.append(Messages.TRIANGLE_SIDE_B).append(sideB).append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE).append(computeAngleOppositeB())
                .append(ShapeConstants.DEGREES)
                .append(EOL);

        builder.append(Messages.TRIANGLE_SIDE_C).append(sideC).append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE).append(computeAngleOppositeC())
                .append(ShapeConstants.DEGREES)
                .append(EOL);

        return builder;
    }
}
