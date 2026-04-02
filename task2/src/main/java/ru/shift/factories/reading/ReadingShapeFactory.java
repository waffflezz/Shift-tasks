package ru.shift.factories.reading;

import ru.shift.factories.ShapeFactory;
import ru.shift.io.InputReader;
import ru.shift.shapes.Shape;

public interface ReadingShapeFactory<S extends Shape> extends ShapeFactory<S, InputReader> {
}
