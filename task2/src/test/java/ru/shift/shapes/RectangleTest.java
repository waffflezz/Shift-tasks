package ru.shift.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.rectangle.RectangleSidesBelowZeroException;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    @ParameterizedTest
    @CsvSource({
            "2, 3",
            "5.5, 10.1",
            "100, 200"
    })
    @DisplayName("Должен корректно создавать прямоугольник")
    void shouldCreateRectangle(double width, double height) {
        // Arrange

        // Act
        Rectangle rectangle = new Rectangle(width, height);

        // Assert
        assertAll(
                () -> assertEquals(width, rectangle.getWidth()),
                () -> assertEquals(height, rectangle.getHeight())
        );
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 0",
            "-1, 5",
            "5, -10",
            "-1, -1"
    })
    @DisplayName("Должен выбрасывать исключение при невалидных сторонах")
    void shouldThrowExceptionWhenSidesInvalid(double width, double height) {
        // Arrange

        // Act
        RectangleSidesBelowZeroException exception = assertThrows(
                RectangleSidesBelowZeroException.class,
                () -> new Rectangle(width, height)
        );

        // Assert
        assertEquals(Messages.RECTANGLE_SIDES_BELOW_ZERO_EXCEPTION, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, 2",
            "10, 5, 5",
            "7, 7, 7"
    })
    @DisplayName("Должен корректно возвращать минимальную сторону")
    void shouldReturnMinSide(double width, double height, double expected) {
        // Arrange
        Rectangle rectangle = new Rectangle(width, height);

        // Act
        double actual = rectangle.getMinSide();

        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, 3",
            "10, 5, 10",
            "7, 7, 7"
    })
    @DisplayName("Должен корректно возвращать максимальную сторону")
    void shouldReturnMaxSide(double width, double height, double expected) {
        // Arrange
        Rectangle rectangle = new Rectangle(width, height);

        // Act
        double actual = rectangle.getMaxSide();

        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5",
            "5, 12, 13",
            "8, 15, 17"
    })
    @DisplayName("Должен корректно вычислять диагональ (теорема Пифагора)")
    void shouldComputeDiagonal(double width, double height, double expected) {
        // Arrange
        Rectangle rectangle = new Rectangle(width, height);

        // Act
        double actual = rectangle.computeDiagonal();

        // Assert
        assertEquals(expected, actual, ShapeTestSuite.STANDARD_DELTA);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, 6",
            "5, 10, 50",
            "7, 7, 49"
    })
    @DisplayName("Должен корректно вычислять площадь")
    void shouldComputeArea(double width, double height, double expected) {
        // Arrange
        Rectangle rectangle = new Rectangle(width, height);

        // Act
        double actual = rectangle.computeArea();

        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, 10",
            "5, 10, 30",
            "7, 7, 28"
    })
    @DisplayName("Должен корректно вычислять периметр")
    void shouldComputePerimeter(double width, double height, double expected) {
        // Arrange
        Rectangle rectangle = new Rectangle(width, height);

        // Act
        double actual = rectangle.computePerimeter();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Должен корректно отдавать тип фигуры")
    void shouldCorrectGetShapeType() {
        // Arrange
        Rectangle rectangle = new Rectangle(5, 5);

        // Act
        String shapeType = rectangle.getShapeType();

        // Assert
        assertEquals("RECTANGLE", shapeType);
    }
}