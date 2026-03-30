package ru.shift.format;

import ru.shift.shapes.Shape;

public interface FormatterFactory<O> {
    <S extends Shape> ShapeFormatter<S, O> getFormatter(S shape);
}
