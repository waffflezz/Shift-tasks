package ru.shift.format.string;

import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Rectangle;

import static ru.shift.constants.IOConstants.EOL;

public class RectangleStringFormatter extends StringFormatter<Rectangle> {

    @Override
    protected void appendSpecificData(StringBuilder builder, Rectangle shape) {
        builder.append(Messages.RECTANGLE_DIAGONAL)
                .append(shape.computeDiagonal())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        builder.append(Messages.RECTANGLE_LENGTH)
                .append(shape.getLength())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        builder.append(Messages.RECTANGLE_WIDTH)
                .append(shape.getWidth())
                .append(ShapeConstants.UNITS)
                .append(EOL);
    }
}
