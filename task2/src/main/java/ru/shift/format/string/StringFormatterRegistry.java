package ru.shift.format.string;

import ru.shift.format.FormatterFactory;
import ru.shift.format.ShapeFormatter;
import ru.shift.shapes.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * Реестр форматтеров строкового представления фигур.
 *
 * <p>Форматтеры регистрируются статически через метод {@link #registerFormatter(Class, ShapeFormatter)}.</p>
 */
public class StringFormatterRegistry implements FormatterFactory<String> {
    private static final Map<Class<? extends Shape>, ShapeFormatter<?, String>> stringFormatters = new HashMap<>();

    public static <S extends Shape> void registerFormatter(Class<S> sClass, ShapeFormatter<S, String> formatter) {
        stringFormatters.put(sClass, formatter);
    }

    @Override
    public <S extends Shape> ShapeFormatter<S, String> getFormatter(S shape) {
        //noinspection unchecked
        return (ShapeFormatter<S, String>) stringFormatters.get(shape.getClass());
    }
}
