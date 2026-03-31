package ru.shift.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.shapes.types.ShapeType;

import static org.junit.jupiter.api.Assertions.*;

class FactoriesValidatorTest {
    @Test
    @DisplayName("Не должен выбрасывать исключение при корректном количестве параметров")
    void shouldNotThrowWhenParamsCountIsValid() {
        // Arrange
        Object[] params = {"1", "2", "3"};
        int expectedCount = 3;

        // Act & Assert
        assertDoesNotThrow(() ->
                FactoriesValidator.validateParamsCount(params, expectedCount, ShapeType.TRIANGLE)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 5})
    @DisplayName("Должен выбрасывать исключение при некорректном количестве параметров")
    void shouldThrowWhenParamsCountInvalid(int actualSize) {
        // Arrange
        Object[] params = new Object[actualSize];
        int expectedCount = 3;

        // Act
        WrongParamCountException exception = assertThrows(
                WrongParamCountException.class,
                () -> FactoriesValidator.validateParamsCount(params, expectedCount, ShapeType.TRIANGLE)
        );

        // Assert
        assertNotNull(exception.getMessage());
    }
}