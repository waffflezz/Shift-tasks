package ru.shift.shapes;

import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.types.ShapeType;

import static ru.shift.constants.IOConstants.EOL;

public class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        Validator.validateCircleRadius(radius);

        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double computeDiameter() {
        return 2 * radius;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CIRCLE;
    }

    @Override
    public double computeArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double computePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public StringBuilder getSpecifiedData() {
        StringBuilder builder = new StringBuilder();

        builder.append(Messages.CIRCLE_RADIUS)
                .append(getRadius())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        builder.append(Messages.CIRCLE_DIAMETER)
                .append(computeDiameter())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        return builder;
    }
}
