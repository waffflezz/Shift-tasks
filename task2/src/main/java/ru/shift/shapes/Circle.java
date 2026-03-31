package ru.shift.shapes;

import lombok.Getter;
import ru.shift.exceptions.circle.CircleRadiusBelowZeroOrZeroException;
import ru.shift.shapes.types.ShapeType;

/**
 * Класс, представляющий окружность.
 *
 * <p>Окружность задаётся радиусом.
 * При создании выполняется валидация радиуса (см. {@link ShapesValidator}).</p>
 */
@Getter
public class Circle extends Shape {
    private final double radius;

    /**
     * Создаёт окружность с заданным радиусом.
     *
     * @param radius радиус окружности
     * @throws CircleRadiusBelowZeroOrZeroException если радиус <= 0
     */
    public Circle(double radius) {
        ShapesValidator.validateCircleRadius(radius);

        this.radius = radius;
    }

    /**
     * Вычисляет диаметр окружности.
     *
     * @return диаметр (2 × {@link #radius})
     */
    public double computeDiameter() {
        return 2 * radius;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CIRCLE;
    }

    @Override
    public double computeArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double computePerimeter() {
        return 2 * Math.PI * radius;
    }
}
