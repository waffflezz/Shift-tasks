package ru.shift.shapes.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.UnknownShapeTypeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapperTest {
    @ParameterizedTest
    @CsvSource({
            "CIRCLE, CIRCLE",
            "RECTANGLE, RECTANGLE",
            "TRIANGLE, TRIANGLE"
    })
    @DisplayName("Должен корректно маппить строку в ShapeType")
    void shouldMapStringToShapeType(String input, ShapeType expected) {
        // Arrange

        // Act
        ShapeType actual = Mapper.fromStringToShapeType(input);

        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "circle",
            "rectangle",
            "triangle",
            "SQUARE",
            "123",
            "UNKNOWN"
    })
    @DisplayName("Должен выбрасывать исключение при неизвестном типе")
    void shouldThrowExceptionForUnknownType(String input) {
        // Arrange

        // Act
        UnknownShapeTypeException exception = assertThrows(
                UnknownShapeTypeException.class,
                () -> Mapper.fromStringToShapeType(input)
        );

        // Assert
        assertEquals(Messages.UNKNOWN_SHAPE_TYPE.formatted(input), exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "'   '"
    })
    @DisplayName("Должен выбрасывать исключение для пустых строк")
    void shouldThrowExceptionForBlankStrings(String input) {
        // Arrange

        // Act
        assertThrows(
                UnknownShapeTypeException.class,
                () -> Mapper.fromStringToShapeType(input)
        );

        // Assert
    }
}