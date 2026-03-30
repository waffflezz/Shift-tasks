package ru.shift.exceptions.triangle;

import ru.shift.constants.Messages;

/**
 * Исключение, выбрасываемое при попытке создания треугольника
 * с некорректными сторонами (значение ≤ 0).
 */
public class TriangleSideBelowZeroException extends IllegalArgumentException {
    /**
     * Создаёт исключение с сообщением о том,
     * что стороны треугольника должны быть положительными.
     */
    public TriangleSideBelowZeroException() {
        super(Messages.TRIANGLE_SIDES_BELOW_ZERO_EXCEPTION);
    }
}
