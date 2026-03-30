package ru.shift.factories;

import ru.shift.shapes.Shape;

public interface ShapeFactory<S extends Shape> {
    S create(String[] params);
}
