package ru.shift.format.string;

import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.format.ShapeFormatter;
import ru.shift.shapes.Shape;

/**
 * Абстрактный базовый класс для форматирования фигур в строковое представление.
 *
 * @param <S> тип фигуры
 */
public abstract class StringFormatter<S extends Shape> implements ShapeFormatter<S, String> {
    @Override
    public final String format(S shape, String shapeType) {
        StringBuilder builder = new StringBuilder();

        appendCommonData(builder, shape, shapeType);
        appendSpecificData(builder, shape);

        return builder.toString();
    }

    /**
     * Добавляет в {@link StringBuilder} общие данные о фигуре:
     * тип, площадь и периметр.
     *
     * @param builder билдeр строки
     * @param shape фигура
     */
    protected void appendCommonData(StringBuilder builder, S shape, String shapeType) {
        builder.append(Messages.SHAPE_TYPE)
                .append(shapeType)
                .append(IOConstants.EOL);

        builder.append(Messages.SHAPE_AREA)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.computeArea()))
                .append(ShapeConstants.SQUARE)
                .append(IOConstants.EOL);

        builder.append(Messages.SHAPE_PERIMETER)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.computePerimeter()))
                .append(ShapeConstants.UNITS)
                .append(IOConstants.EOL);
    }

    /**
     * Добавляет в {@link StringBuilder} специфичные данные фигуры.
     *
     * <p>Должен быть реализован в подклассах.</p>
     *
     * @param builder билдeр строки
     * @param shape фигура
     */
    protected abstract void appendSpecificData(StringBuilder builder, S shape);

    protected abstract Class<S> getShapeClass();
}
