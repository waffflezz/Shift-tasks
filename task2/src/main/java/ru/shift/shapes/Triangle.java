package ru.shift.shapes;

import lombok.Getter;
import ru.shift.exceptions.triangle.TriangleCantExistsException;
import ru.shift.exceptions.triangle.TriangleSideBelowZeroException;

/**
 * Класс, представляющий треугольник, заданный длинами трёх сторон.
 *
 * <p>При создании выполняется валидация сторон (см. {@link ShapesValidator}),
 * включая проверку положительности значений и неравенства треугольника.</p>
 */
@Getter
public final class Triangle implements Shape {
    private static final String TYPE = "TRIANGLE";

    private final double sideA;
    private final double sideB;
    private final double sideC;

    /**
     * Создаёт треугольник по трём сторонам.
     *
     * @param sideA длина стороны A
     * @param sideB длина стороны B
     * @param sideC длина стороны C
     * @throws TriangleSideBelowZeroException если хотя бы одна и сторон меньше или равна 0
     * @throws TriangleCantExistsException если треугольник с такими сторонами не может существовать
     */
    public Triangle(double sideA, double sideB, double sideC) {
        ShapesValidator.validateTriangleCreation(sideA, sideB, sideC);

        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    /**
     * Вычисляет угол, противоположный стороне {@link #sideA}, в градусах.
     *
     * <p>Расчёт выполняется по теореме косинусов.</p>
     *
     * @return угол напротив стороны {@link #sideA} в градусах
     */
    public double computeAngleOppositeA() {
        return Math.toDegrees(
                Math.acos((sideB * sideB + sideC * sideC - sideA * sideA) / (2 * sideB * sideC))
        );
    }

    /**
     * Вычисляет угол, противоположный стороне {@link #sideB}, в градусах.
     *
     * <p>Расчёт выполняется по теореме косинусов.</p>
     *
     * @return угол напротив стороны {@link #sideB} в градусах
     */
    public double computeAngleOppositeB() {
        return Math.toDegrees(
                Math.acos((sideA * sideA + sideC * sideC - sideB * sideB) / (2 * sideA * sideC))
        );
    }

    /**
     * Вычисляет угол, противоположный стороне {@link #sideC}, в градусах.
     *
     * <p>Расчёт выполняется по теореме косинусов.</p>
     *
     * @return угол напротив стороны {@link #sideC} в градусах
     */
    public double computeAngleOppositeC() {
        return Math.toDegrees(
                Math.acos((sideA * sideA + sideB * sideB - sideC * sideC) / (2 * sideA * sideB))
        );
    }

    @Override
    public double computeArea() {
        double s = computePerimeter() / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    public double computePerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String getShapeType() {
        return TYPE;
    }
}
