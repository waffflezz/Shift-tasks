package ru.shift.shapes;

import ru.shift.shapes.types.ShapeType;

public class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        ShapesValidator.validateCircleRadius(radius);

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
}
