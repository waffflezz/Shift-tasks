package ru.shift.factories;

import ru.shift.factories.reading.ReadingShapeFactory;
import ru.shift.shapes.Shape;

import java.util.Optional;

public interface ShapeFactoryProvider {
    <S extends Shape> Optional<ReadingShapeFactory<S>> getFactory(String shape);
}
