package ru.shift.format.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Circle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CircleStringFormatterTest {
    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.5, 10.0})
    @DisplayName("Должен корректно форматировать окружность")
    void shouldFormatCircle(double radius) {
        // Arrange
        Circle circle = new Circle(radius);
        CircleStringFormatter formatter = new CircleStringFormatter();
        String expected = buildExpectedCircleString(circle);

        // Act
        String actual = formatter.format(circle);

        // Assert
        assertEquals(expected, actual);
    }

    private String buildExpectedCircleString(Circle circle) {
        return Messages.SHAPE_TYPE
                + circle.getShapeType()
                + IOConstants.EOL
                + Messages.SHAPE_AREA
                + ShapeConstants.DECIMAL_FORMAT.format(circle.computeArea())
                + ShapeConstants.SQUARE
                + IOConstants.EOL
                + Messages.SHAPE_PERIMETER
                + ShapeConstants.DECIMAL_FORMAT.format(circle.computePerimeter())
                + ShapeConstants.UNITS
                + IOConstants.EOL
                + Messages.CIRCLE_RADIUS
                + ShapeConstants.DECIMAL_FORMAT.format(circle.getRadius())
                + ShapeConstants.UNITS
                + IOConstants.EOL
                + Messages.CIRCLE_DIAMETER
                + ShapeConstants.DECIMAL_FORMAT.format(circle.computeDiameter())
                + ShapeConstants.UNITS
                + IOConstants.EOL;
    }
}