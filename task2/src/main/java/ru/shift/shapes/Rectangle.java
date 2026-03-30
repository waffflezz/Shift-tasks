package ru.shift.shapes;

import ru.shift.shapes.types.ShapeType;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        ShapesValidator.validateRectangleSides(width, height);

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
}
