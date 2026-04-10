package ru.shift.format.string;

import ru.shift.format.FormatterRegistry;
import ru.shift.format.ShapeFormatter;
import ru.shift.shapes.Shape;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Реестр форматтеров строкового представления фигур.
 */
public class StringFormatterRegistry implements FormatterRegistry<String> {
    private final Map<Class<? extends Shape>, ShapeFormatter<?, String>> stringFormatters = new HashMap<>();

    public StringFormatterRegistry() {
        ServiceLoader.load(StringFormatter.class).forEach(this::registerFormatter);
    }

    private <S extends Shape> void registerFormatter(StringFormatter<S> formatter) {
        Class<S> shapeClass = formatter.getShapeClass();
        stringFormatters.put(shapeClass, formatter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Shape> ShapeFormatter<S, String> getFormatter(S shape) {
        return (ShapeFormatter<S, String>) stringFormatters.get(shape.getClass());
    }
}
