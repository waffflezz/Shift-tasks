package ru.shift.exceptions.rectangle;

import ru.shift.constants.Messages;

/**
 * Исключение, выбрасываемое при попытке создания прямоугольника
 * с некорректными сторонами (значение ≤ 0).
 */
public class RectangleSidesBelowZeroException extends IllegalArgumentException {
    /**
     * Создаёт исключение с сообщением о том,
     * что стороны прямоугольника должны быть положительными.
     */
    public RectangleSidesBelowZeroException() {
        super(Messages.RECTANGLE_SIDES_BELOW_ZERO_EXCEPTION);
    }
}
