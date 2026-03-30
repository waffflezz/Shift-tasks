package ru.shift.format.string;

import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Circle;

/**
 * Реализация {@link StringFormatter} для форматирования окружности.
 *
 * <p>Дополняет общее строковое представление фигуры специфичными данными:
 * <ul>
 *     <li>радиус ({@link Circle#getRadius()})</li>
 *     <li>диаметр ({@link Circle#computeDiameter()})</li>
 * </ul>
 *
 * <p>Все числовые значения форматируются с использованием
 * {@link ShapeConstants#DECIMAL_FORMAT}.</p>
 */
public class CircleStringFormatter extends StringFormatter<Circle> {
    @Override
    protected void appendSpecificData(StringBuilder builder, Circle shape) {
        builder.append(Messages.CIRCLE_RADIUS)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.getRadius()))
                .append(ShapeConstants.UNITS)
                .append(IOConstants.EOL);

        builder.append(Messages.CIRCLE_DIAMETER)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.computeDiameter()))
                .append(ShapeConstants.UNITS)
                .append(IOConstants.EOL);
    }
}
