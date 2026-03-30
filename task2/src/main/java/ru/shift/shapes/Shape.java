package ru.shift.shapes;

import ru.shift.shapes.types.ShapeType;

/**
 * Абстрактный базовый класс для геометрических фигур.
 */
public abstract class Shape {
    /**
     * Возвращает тип геометрической фигуры.
     *
     * @return тип фигуры ({@link ShapeType})
     */
    public abstract ShapeType getShapeType();

    /**
     * Вычисляет площадь фигуры.
     *
     * @return площадь фигуры
     */
    public abstract double computeArea();

    /**
     * Вычисляет периметр фигуры.
     *
     * @return периметр фигуры
     */
    public abstract double computePerimeter();
}
