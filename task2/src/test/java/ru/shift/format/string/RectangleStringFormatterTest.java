package ru.shift.format.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.constants.IOConstants;
import ru.shift.constants.Messages;
import ru.shift.constants.ShapeConstants;
import ru.shift.shapes.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleStringFormatterTest {

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "5, 10",
            "7.5, 2.5"
    })
    @DisplayName("Должен корректно форматировать прямоугольник")
    void shouldFormatRectangle(double width, double height) {
        // Arrange
        Rectangle rectangle = new Rectangle(width, height);
        RectangleStringFormatter formatter = new RectangleStringFormatter();

        String expected = buildExpectedRectangleString(rectangle);

        // Act
        String actual = formatter.format(rectangle);

        // Assert
        assertEquals(expected, actual);
    }

    private String buildExpectedRectangleString(Rectangle rectangle) {
        return Messages.SHAPE_TYPE
                + rectangle.getShapeType()
                + IOConstants.EOL
                + Messages.SHAPE_AREA
                + ShapeConstants.DECIMAL_FORMAT.format(rectangle.computeArea())
                + ShapeConstants.SQUARE
                + IOConstants.EOL
                + Messages.SHAPE_PERIMETER
                + ShapeConstants.DECIMAL_FORMAT.format(rectangle.computePerimeter())
                + ShapeConstants.UNITS
                + IOConstants.EOL
                + Messages.RECTANGLE_DIAGONAL
                + ShapeConstants.DECIMAL_FORMAT.format(rectangle.computeDiagonal())
                + ShapeConstants.UNITS
                + IOConstants.EOL
                + Messages.RECTANGLE_LENGTH
                + ShapeConstants.DECIMAL_FORMAT.format(rectangle.getMaxSide())
                + ShapeConstants.UNITS
                + IOConstants.EOL
                + Messages.RECTANGLE_WIDTH
                + ShapeConstants.DECIMAL_FORMAT.format(rectangle.getMinSide())
                + ShapeConstants.UNITS
                + IOConstants.EOL;
    }
}