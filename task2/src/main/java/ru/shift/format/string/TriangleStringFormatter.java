package ru.shift.format.string;

import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Triangle;

public class TriangleStringFormatter extends StringFormatter<Triangle> {
    @Override
    protected void appendSpecificData(StringBuilder builder, Triangle shape) {
        builder.append(Messages.TRIANGLE_SIDE_A)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.getSideA()))
                .append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.computeAngleOppositeA()))
                .append(ShapeConstants.DEGREES)
                .append(IOConstants.EOL);

        builder.append(Messages.TRIANGLE_SIDE_B)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.getSideB()))
                .append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.computeAngleOppositeB()))
                .append(ShapeConstants.DEGREES)
                .append(IOConstants.EOL);

        builder.append(Messages.TRIANGLE_SIDE_C)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.getSideC()))
                .append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE)
                .append(ShapeConstants.DECIMAL_FORMAT.format(shape.computeAngleOppositeC()))
                .append(ShapeConstants.DEGREES)
                .append(IOConstants.EOL);
    }
}
