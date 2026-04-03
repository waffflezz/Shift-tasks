package ru.shift.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.triangle.TriangleCantExistsException;
import ru.shift.exceptions.triangle.TriangleSideBelowZeroException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TriangleTest {
    @ParameterizedTest
    @CsvSource({
            "3, 4, 5",
            "5, 5, 6",
            "7.5, 8.5, 9.5"
    })
    @DisplayName("Должен корректно создавать треугольник с валидными сторонами")
    void shouldCreateTriangleWithValidSides(double sideA, double sideB, double sideC) {
        // Arrange

        // Act
        Triangle triangle = new Triangle(sideA, sideB, sideC);

        // Assert
        assertEquals(sideA, triangle.getSideA());
        assertEquals(sideB, triangle.getSideB());
        assertEquals(sideC, triangle.getSideC());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, 5",
            "3, 0, 5",
            "3, 4, 0",
            "-1, 4, 5",
            "3, -1, 5",
            "3, 4, -1"
    })
    @DisplayName("Должен выбрасывать исключение при неположительных сторонах")
    void shouldThrowExceptionWhenAnySideIsZeroOrNegative(double sideA, double sideB, double sideC) {
        // Arrange

        // Act
        TriangleSideBelowZeroException exception = assertThrows(
                TriangleSideBelowZeroException.class,
                () -> new Triangle(sideA, sideB, sideC)
        );

        // Assert
        assertEquals(Messages.TRIANGLE_SIDES_BELOW_ZERO_EXCEPTION, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "10, 1, 1",
            "5, 1, 3",
            "100, 20, 30"
    })
    @DisplayName("Должен выбрасывать исключение, если треугольник не существует")
    void shouldThrowExceptionWhenTriangleCannotExist(double sideA, double sideB, double sideC) {
        // Arrange

        // Act
        TriangleCantExistsException exception = assertThrows(
                TriangleCantExistsException.class,
                () -> new Triangle(sideA, sideB, sideC)
        );

        // Assert
        assertEquals(Messages.TRIANGLE_CANT_EXISTS_EXCEPTION.formatted(sideA, sideB, sideC), exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5, 90.0",
            "5, 5, 6, 73.73979529168804"
    })
    @DisplayName("Должен корректно вычислять угол напротив стороны C")
    void shouldComputeAngleOppositeC(double sideA, double sideB, double sideC, double expectedAngle) {
        // Arrange
        Triangle triangle = new Triangle(sideA, sideB, sideC);

        // Act
        double actualAngle = triangle.computeAngleOppositeC();

        // Assert
        assertEquals(expectedAngle, actualAngle, ShapeTestSuite.STANDARD_DELTA);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5, 36.86989764584401",
            "5, 5, 6, 53.13010235415599"
    })
    @DisplayName("Должен корректно вычислять угол напротив стороны A")
    void shouldComputeAngleOppositeA(double sideA, double sideB, double sideC, double expectedAngle) {
        // Arrange
        Triangle triangle = new Triangle(sideA, sideB, sideC);

        // Act
        double actualAngle = triangle.computeAngleOppositeA();

        // Assert
        assertEquals(expectedAngle, actualAngle, ShapeTestSuite.STANDARD_DELTA);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5, 53.13010235415599",
            "5, 5, 6, 53.13010235415599"
    })
    @DisplayName("Должен корректно вычислять угол напротив стороны B")
    void shouldComputeAngleOppositeB(double sideA, double sideB, double sideC, double expectedAngle) {
        // Arrange
        Triangle triangle = new Triangle(sideA, sideB, sideC);

        // Act
        double actualAngle = triangle.computeAngleOppositeB();

        // Assert
        assertEquals(expectedAngle, actualAngle, ShapeTestSuite.STANDARD_DELTA);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5, 12",
            "5, 5, 6, 16",
            "7.5, 8.5, 9.5, 25.5"
    })
    @DisplayName("Должен корректно вычислять периметр")
    void shouldComputePerimeter(double sideA, double sideB, double sideC, double expectedPerimeter) {
        // Arrange
        Triangle triangle = new Triangle(sideA, sideB, sideC);

        // Act
        double actualPerimeter = triangle.computePerimeter();

        // Assert
        assertEquals(expectedPerimeter, actualPerimeter, ShapeTestSuite.STANDARD_DELTA);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5, 6",
            "5, 5, 6, 12",
            "7, 8, 9, 26.832815729997478"
    })
    @DisplayName("Должен корректно вычислять площадь")
    void shouldComputeArea(double sideA, double sideB, double sideC, double expectedArea) {
        // Arrange
        Triangle triangle = new Triangle(sideA, sideB, sideC);

        // Act
        double actualArea = triangle.computeArea();

        // Assert
        assertEquals(expectedArea, actualArea, ShapeTestSuite.STANDARD_DELTA);
    }
}