package ru.shift.exceptions.circle;

import ru.shift.constants.Messages;

/**
 * Исключение, выбрасываемое при попытке создания окружности
 * с некорректным радиусом (значение ≤ 0).
 */
public class CircleRadiusBelowZeroOrZeroException extends IllegalArgumentException {
    /**
     * Создаёт исключение с сообщением о том,
     * что радиус окружности должен быть положительным.
     */
    public CircleRadiusBelowZeroOrZeroException() {
        super(Messages.CIRCLE_RADIUS_BELOW_ZERO_EXCEPTION);
    }
}
