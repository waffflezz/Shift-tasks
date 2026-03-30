package ru.shift.format.string;

import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Triangle;

import static ru.shift.constants.IOConstants.EOL;

public class TriangleStringFormatter extends StringFormatter<Triangle> {
    @Override
    protected void appendSpecificData(StringBuilder builder, Triangle shape) {
        builder.append(Messages.TRIANGLE_SIDE_A).append(shape.getSideA()).append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE).append(shape.computeAngleOppositeA())
                .append(ShapeConstants.DEGREES)
                .append(EOL);

        builder.append(Messages.TRIANGLE_SIDE_B).append(shape.getSideB()).append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE).append(shape.computeAngleOppositeB())
                .append(ShapeConstants.DEGREES)
                .append(EOL);

        builder.append(Messages.TRIANGLE_SIDE_C).append(shape.getSideC()).append(ShapeConstants.UNITS)
                .append(Messages.TRIANGLE_OPPOSITE_ANGLE).append(shape.computeAngleOppositeC())
                .append(ShapeConstants.DEGREES)
                .append(EOL);
    }
}
