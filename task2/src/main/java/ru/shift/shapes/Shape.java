package ru.shift.shapes;

import ru.shift.shapes.types.ShapeType;

public abstract class Shape {
    public abstract ShapeType getShapeType();

    public abstract double computeArea();

    public abstract double computePerimeter();
}
