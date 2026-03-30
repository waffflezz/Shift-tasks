package ru.shift.factories;

import ru.shift.shapes.Shape;
import ru.shift.shapes.types.ShapeType;

public interface ShapeFactory<S extends Shape> {
    ShapeType getShapeType();
    S create(String[] params);
    int getParamsNeedCount();
}
