package ru.shift.format.string;

import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.format.ShapeFormatter;
import ru.shift.shapes.Shape;

public abstract class StringFormatter<S extends Shape> implements ShapeFormatter<S, String> {
    @Override
    public final String format(S shape) {
        StringBuilder builder = new StringBuilder();

        appendCommonData(builder, shape);
        appendSpecificData(builder, shape);

        return builder.toString();
    }

    protected void appendCommonData(StringBuilder builder, S shape) {
        builder.append(Messages.SHAPE_TYPE)
                .append(shape.getShapeType())
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

    protected abstract void appendSpecificData(StringBuilder builder, S shape);
}
