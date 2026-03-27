package ru.shift.shapes;

import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.types.ShapeType;

import static ru.shift.constants.IOConstants.EOL;

public abstract class Shape {
    public String getShapeStringData() {
        StringBuilder builder = new StringBuilder();

        builder.append(Messages.SHAPE_TYPE)
                .append(getShapeType())
                .append(EOL);

        builder.append(Messages.SHAPE_AREA)
                .append(computeArea())
                .append(ShapeConstants.SQUARE)
                .append(EOL);

        builder.append(Messages.SHAPE_PERIMETER)
                .append(computePerimeter())
                .append(ShapeConstants.UNITS)
                .append(EOL);

        builder.append(getSpecifiedData());

        return builder.toString();
    }

    public abstract ShapeType getShapeType();

    public abstract double computeArea();

    public abstract double computePerimeter();

    public abstract StringBuilder getSpecifiedData();
}
