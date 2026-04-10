package ru.shift.format.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Triangle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleStringFormatterTest {
    private static final String SHAPE_TYPE = "TRIANGLE";

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5",
            "5, 5, 6",
            "7, 8, 9"
    })
    @DisplayName("Должен корректно форматировать треугольник")
    void shouldFormatTriangle(double sideA, double sideB, double sideC) {
        // Arrange
        Triangle triangle = new Triangle(sideA, sideB, sideC);
        TriangleStringFormatter formatter = new TriangleStringFormatter();
        String expected = buildExpectedTriangleString(triangle);

        // Act
        String actual = formatter.format(triangle, SHAPE_TYPE);

        // Assert
        assertEquals(expected, actual);
    }

    private String buildExpectedTriangleString(Triangle triangle) {
        return Messages.SHAPE_TYPE
                + SHAPE_TYPE
                + IOConstants.EOL
                + Messages.SHAPE_AREA
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.computeArea())
                + ShapeConstants.SQUARE
                + IOConstants.EOL
                + Messages.SHAPE_PERIMETER
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.computePerimeter())
                + ShapeConstants.UNITS
                + IOConstants.EOL
                + Messages.TRIANGLE_SIDE_A
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.getSideA())
                + ShapeConstants.UNITS
                + Messages.TRIANGLE_OPPOSITE_ANGLE
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.computeAngleOppositeA())
                + ShapeConstants.DEGREES
                + IOConstants.EOL
                + Messages.TRIANGLE_SIDE_B
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.getSideB())
                + ShapeConstants.UNITS
                + Messages.TRIANGLE_OPPOSITE_ANGLE
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.computeAngleOppositeB())
                + ShapeConstants.DEGREES
                + IOConstants.EOL
                + Messages.TRIANGLE_SIDE_C
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.getSideC())
                + ShapeConstants.UNITS
                + Messages.TRIANGLE_OPPOSITE_ANGLE
                + ShapeConstants.DECIMAL_FORMAT.format(triangle.computeAngleOppositeC())
                + ShapeConstants.DEGREES
                + IOConstants.EOL;
    }
}