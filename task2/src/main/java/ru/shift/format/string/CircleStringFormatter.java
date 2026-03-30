package ru.shift.format.string;


import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Circle;

import static ru.shift.constants.IOConstants.EOL;

public class CircleStringFormatter extends StringFormatter<Circle> {
    @Override
    protected void appendSpecificData(StringBuilder builder, Circle shape) {
        builder.append(Messages.CIRCLE_RADIUS)
                .append(shape.getRadius())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        builder.append(Messages.CIRCLE_DIAMETER)
                .append(shape.computeDiameter())
                .append(ShapeConstants.UNITS)
                .append(EOL);
    }
}
