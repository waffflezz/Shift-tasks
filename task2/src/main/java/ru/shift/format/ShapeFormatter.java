package ru.shift.format;

import ru.shift.shapes.Shape;

public interface ShapeFormatter<S extends Shape, O> {
    O format(S shape);
}
