package ru.shift.factories;

import ru.shift.shapes.Shape;

import java.util.Optional;

/**
 * Провайдер фабрик для создания фигур.
 *
 * <p>Предоставляет доступ к зарегистрированным реализациям фабрик
 * по строковому представлению типа фигуры.</p>
 *
 * <p>Используется как точка входа для получения конкретной фабрики,
 * которая умеет создавать {@link Shape} из определённого источника
 * данных (например, {@link ru.shift.io.InputReader}, JSON и т.д.).</p>
 *
 * <p>Реализации могут использовать различные механизмы регистрации фабрик</p>
 *
 * @param <F> тип фабрики (например, {@code ReadingShapeFactory<?>})
 */
public interface ShapeFactoryProvider<F> {
    Optional<F> getFactory(String shape);
}
