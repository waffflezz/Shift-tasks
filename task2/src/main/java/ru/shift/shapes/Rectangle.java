package ru.shift.shapes;

import lombok.Getter;
import ru.shift.shapes.types.ShapeType;

/**
 * Класс, представляющий прямоугольник.
 *
 * <p>Прямоугольник задаётся шириной и высотой.
 * При создании выполняется валидация сторон (см. {@link ShapesValidator}).</p>
 */
@Getter
public class Rectangle extends Shape {
    private final double width;
    private final double height;

    /**
     * Создаёт прямоугольник с заданными сторонами.
     *
     * @param width ширина
     * @param height высота
     * @throws ru.shift.exceptions.rectangle.RectangleSidesBelowZeroException если одна из сторон <= 0
     */
    public Rectangle(double width, double height) {
        ShapesValidator.validateRectangleSides(width, height);

        this.width = width;
        this.height = height;
    }

    /**
     * Возвращает меньшую сторону прямоугольника.
     *
     * @return минимальная из сторон ({@link #width} или {@link #height})
     */
    public double getMinSide() {
        return Math.min(width, height);
    }

    /**
     * Возвращает большую сторону прямоугольника.
     *
     * @return максимальная из сторон ({@link #width} или {@link #height})
     */
    public double getMaxSide() {
        return Math.max(width, height);
    }

    /**
     * Вычисляет длину диагонали прямоугольника.
     *
     * <p>Расчёт выполняется по теореме Пифагора.</p>
     *
     * @return длина диагонали
     */
    public double computeDiagonal() {
        return Math.sqrt(width * width + height * height);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.RECTANGLE;
    }

    @Override
    public double computeArea() {
        return width * height;
    }

    @Override
    public double computePerimeter() {
        return 2 * (width + height);
    }
}
