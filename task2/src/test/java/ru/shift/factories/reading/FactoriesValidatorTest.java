package ru.shift.factories.reading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.factories.FactoriesValidator;

import static org.junit.jupiter.api.Assertions.*;

class FactoriesValidatorTest {
    @Test
    @DisplayName("Не должен выбрасывать исключение при корректном количестве параметров")
    void shouldNotThrowWhenParamsCountIsValid() {
        // Arrange
        Object[] params = {"1", "2", "3"};
        String shapeType = "TRIANGLE";
        int expectedCount = 3;

        // Act & Assert
        assertDoesNotThrow(() ->
                FactoriesValidator.validateParamsCount(params, expectedCount, shapeType)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 4, 5})
    @DisplayName("Должен выбрасывать исключение при некорректном количестве параметров")
    void shouldThrowWhenParamsCountInvalid(int actualSize) {
        // Arrange
        Object[] params = new Object[actualSize];
        String shapeType = "TRIANGLE";
        int expectedCount = 3;

        // Act
        WrongParamCountException exception = assertThrows(
                WrongParamCountException.class,
                () -> FactoriesValidator.validateParamsCount(params, expectedCount, shapeType)
        );

        // Assert
        assertEquals(
                Messages.WRONG_COUNT_OF_PARAM_EXCEPTION.formatted(shapeType, expectedCount),
                exception.getMessage()
        );
    }
}