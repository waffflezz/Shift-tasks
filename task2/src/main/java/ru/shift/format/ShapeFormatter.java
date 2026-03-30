package ru.shift.format;

import ru.shift.shapes.Shape;

/**
 * Интерфейс для форматирования геометрических фигур.
 *
 * <p>Позволяет преобразовывать объект {@link Shape} в произвольное представление
 * (например, строку, JSON, DTO и т.д.).</p>
 *
 * @param <S> тип фигуры (наследник {@link Shape})
 * @param <O> тип результата форматирования
 */
public interface ShapeFormatter<S extends Shape, O> {
    /**
     * Форматирует переданную фигуру в целевое представление.
     *
     * @param shape фигура для форматирования
     * @return результат форматирования
     */
    O format(S shape);
}
