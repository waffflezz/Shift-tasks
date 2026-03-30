package ru.shift.format;

import ru.shift.shapes.Shape;

/**
 * Фабрика для получения форматтеров геометрических фигур.
 *
 * <p>Предоставляет соответствующий {@link ShapeFormatter}
 * для конкретного типа фигуры.</p>
 *
 * @param <O> тип результата форматирования
 */
public interface FormatterFactory<O> {
    /**
     * Возвращает форматтер для переданной фигуры.
     *
     * <p>Реализация должна определить подходящий форматтер
     * на основе типа фигуры.</p>
     *
     * @param shape фигура, для которой требуется форматтер
     * @param <S> конкретный тип фигуры
     * @return форматтер для переданного типа фигуры
     * @throws IllegalArgumentException если для данного типа фигуры форматтер не найден
     */
    <S extends Shape> ShapeFormatter<S, O> getFormatter(S shape);
}
