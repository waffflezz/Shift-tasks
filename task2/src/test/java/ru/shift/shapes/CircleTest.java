package ru.shift.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.circle.CircleRadiusBelowZeroOrZeroException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleTest {
    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.5, 10.0, 100.0})
    @DisplayName("Должен корректно создавать окружность с валидным радиусом")
    void shouldCreateCircleWithValidRadius(double radius) {
        // Arrange

        // Act
        Circle circle = new Circle(radius);

        // Assert
        assertEquals(radius, circle.getRadius());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -10.5, -100.0})
    @DisplayName("Должен выбрасывать исключение при невалидном радиусе")
    void shouldThrowExceptionWhenRadiusIsZeroOrNegative(double radius) {
        // Arrange

        // Act
        CircleRadiusBelowZeroOrZeroException exception = assertThrows(
                CircleRadiusBelowZeroOrZeroException.class,
                () -> new Circle(radius)
        );

        // Assert
        assertEquals(
                Messages.CIRCLE_RADIUS_BELOW_ZERO_EXCEPTION,
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 3.5, 10.0})
    @DisplayName("Должен корректно вычислять диаметр")
    void shouldComputeCorrectDiameter(double radius) {
        // Arrange
        Circle circle = new Circle(radius);

        // Act
        double actualDiameter = circle.computeDiameter();

        // Assert
        assertEquals(2 * radius, actualDiameter);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 3.5, 10.0})
    @DisplayName("Должен корректно вычислять площадь")
    void shouldComputeCorrectArea(double radius) {
        // Arrange
        Circle circle = new Circle(radius);

        // Act
        double actualArea = circle.computeArea();

        // Assert
        assertEquals(Math.PI * radius * radius, actualArea, ShapeTestSuite.STANDARD_DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 3.5, 10.0})
    @DisplayName("Должен корректно вычислять периметр")
    void shouldComputeCorrectPerimeter(double radius) {
        // Arrange
        Circle circle = new Circle(radius);

        // Act
        double actualPerimeter = circle.computePerimeter();

        // Assert
        assertEquals(2 * Math.PI * radius, actualPerimeter, ShapeTestSuite.STANDARD_DELTA);
    }
}