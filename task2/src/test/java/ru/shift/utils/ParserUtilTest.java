package ru.shift.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.ParseDoubleException;

import static org.junit.jupiter.api.Assertions.*;

class ParserUtilTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("Должен выбрасывать исключение, если строка null или пустая")
    void shouldThrowExceptionWhenStringIsBlank(String input) {
        // Arrange

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> ParserUtil.parsePositiveDouble(input)
        );

        // Assert
        assertEquals(Messages.STRING_IS_BLANK, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "1,23", "--5", "NaN", "Infinity"})
    @DisplayName("Должен выбрасывать исключение, если строка не является числом")
    void shouldThrowExceptionWhenNotANumber(String input) {
        // Arrange

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> ParserUtil.parsePositiveDouble(input)
        );

        // Assert
        assertEquals(Messages.VALUE_MUST_BE_NUMBER, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-10.5"})
    @DisplayName("Должен выбрасывать исключение, если число <= 0")
    void shouldThrowExceptionWhenValueLessOrEqualZero(String input) {
        // Arrange

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> ParserUtil.parsePositiveDouble(input)
        );

        // Assert
        assertEquals(
                Messages.VALUE_MUST_BE_GREATER.formatted(0),
                exception.getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2.5", "100", "0.0001"})
    @DisplayName("Должен корректно парсить положительные числа")
    void shouldParseValidPositiveDouble(String input) {
        // Arrange

        // Act
        double result = ParserUtil.parsePositiveDouble(input);

        // Assert
        assertEquals(Double.parseDouble(input), result);
    }
}