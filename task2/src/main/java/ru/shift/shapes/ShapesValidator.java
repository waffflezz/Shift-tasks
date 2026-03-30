package ru.shift.shapes;

import ru.shift.exceptions.circle.CircleRadiusBelowZeroException;
import ru.shift.exceptions.triangle.TriangleCantExistsException;
import ru.shift.exceptions.triangle.TriangleSideBelowZeroException;
import ru.shift.exceptions.rectangle.RectangleSidesBelowZeroException;

/**
 * Класс для валидации параметров геометрических фигур.
 *
 * <p>Содержит набор статических методов для проверки корректности входных данных
 * при создании фигур. В случае некорректных значений выбрасываются соответствующие исключения.</p>
 */
public class ShapesValidator {
    /**
     * Проверяет корректность сторон треугольника.
     *
     * <p>Валидация включает:
     * <ul>
     *     <li>проверку, что все стороны больше 0</li>
     *     <li>проверку неравенства треугольника</li>
     * </ul>
     *
     * @param sideA сторона A
     * @param sideB сторона B
     * @param sideC сторона C
     * @throws TriangleSideBelowZeroException если хотя бы одна сторона ≤ 0
     * @throws TriangleCantExistsException если треугольник с такими сторонами не существует
     */
    public static void validateTriangleCreation(double sideA, double sideB, double sideC) {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new TriangleSideBelowZeroException();
        }

        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new TriangleCantExistsException(sideA, sideB, sideC);
        }
    }

    /**
     * Проверяет корректность радиуса окружности.
     *
     * @param radius радиус окружности
     * @throws CircleRadiusBelowZeroException если радиус ≤ 0
     */
    public static void validateCircleRadius(double radius) {
        if (radius <= 0) {
            throw new CircleRadiusBelowZeroException();
        }
    }

    /**
     * Проверяет корректность сторон прямоугольника.
     *
     * @param width ширина прямоугольника
     * @param height высота прямоугольника
     * @throws RectangleSidesBelowZeroException если ширина или высота ≤ 0
     */
    public static void validateRectangleSides(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new RectangleSidesBelowZeroException();
        }
    }
}
