package ru.shift.shapes;

import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.types.ShapeType;

import static ru.shift.constants.IOConstants.EOL;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        Validator.validateRectangleSides(width, height);

        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return Math.min(width, height);
    }

    public double getLength() {
        return Math.max(width, height);
    }

    public double computeDiagonal() {
        return Math.sqrt(width * width + height * height);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.RECTANGLE;
    }

    @Override
    public double computeArea() {
        return width * height;
    }

    @Override
    public double computePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public StringBuilder getSpecifiedData() {
        StringBuilder builder = new StringBuilder();

        builder.append(Messages.RECTANGLE_DIAGONAL)
                .append(computeDiagonal())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        builder.append(Messages.RECTANGLE_LENGTH)
                .append(getLength())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        builder.append(Messages.RECTANGLE_WIDTH)
                .append(getWidth())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        return builder;
    }
}
