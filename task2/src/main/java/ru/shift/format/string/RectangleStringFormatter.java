package ru.shift.format.string;

import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Rectangle;

public class RectangleStringFormatter extends StringFormatter<Rectangle> {

    @Override
    protected void appendSpecificData(StringBuilder builder, Rectangle shape) {
        builder.append(Messages.RECTANGLE_DIAGONAL)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.computeDiagonal()))
                .append(ShapeConstants.UNITS)
                .append(IOConstants.EOL);

        builder.append(Messages.RECTANGLE_LENGTH)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.getLength()))
                .append(ShapeConstants.UNITS)
                .append(IOConstants.EOL);

        builder.append(Messages.RECTANGLE_WIDTH)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.getWidth()))
                .append(ShapeConstants.UNITS)
                .append(IOConstants.EOL);
    }
}
