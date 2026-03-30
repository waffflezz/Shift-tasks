package ru.shift.exceptions.triangle;

import ru.shift.constants.Messages;

/**
 * Исключение, выбрасываемое при попытке создания треугольника,
 * который не может существовать.
 */
public class TriangleCantExistsException extends IllegalArgumentException {
    /**
     * Создаёт исключение с сообщением, содержащим значения сторон,
     * для которых треугольник не может существовать.
     *
     * @param sideA сторона A
     * @param sideB сторона B
     * @param sideC сторона C
     */
    public TriangleCantExistsException(double sideA, double sideB, double sideC) {
        super(Messages.TRIANGLE_CANT_EXISTS_EXCEPTION.formatted(sideA, sideB, sideC));
    }
}
