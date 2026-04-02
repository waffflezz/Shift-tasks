package ru.shift.shapes;

/**
 * Интерфейс для геометрических фигур.
 */
public interface Shape {
    /**
     * Вычисляет площадь фигуры.
     *
     * @return площадь фигуры
     */
    double computeArea();

    /**
     * Вычисляет периметр фигуры.
     *
     * @return периметр фигуры
     */
    double computePerimeter();
}
