package ru.shift.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.ParseDoubleException;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.shapes.Rectangle;
import ru.shift.shapes.types.ShapeType;
import ru.shift.utils.ParserUtil;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleFactoryTest {
    private final RectangleFactory factory = new RectangleFactory();

    @Test
    @DisplayName("Должен возвращать тип RECTANGLE")
    void shouldReturnRectangleType() {
        // Arrange

        // Act
        ShapeType actualShapeType = factory.getShapeType();

        // Assert
        assertEquals(ShapeType.RECTANGLE, actualShapeType);
    }

    @Test
    @DisplayName("Должен возвращать нужное количество параметров")
    void shouldReturnParamsNeedCount() {
        // Arrange

        // Act
        int actualParamsNeedCount = factory.getParamsNeedCount();

        // Assert
        assertEquals(2, actualParamsNeedCount);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "5.5, 10.1",
            "100, 200"
    })
    @DisplayName("Должен корректно создавать прямоугольник")
    void shouldCreateRectangle(String width, String height) {
        // Arrange
        String[] params = {width, height};

        // Act
        Rectangle actualRectangle = factory.create(params);

        // Assert
        assertAll(
                () -> assertEquals(Double.parseDouble(width), actualRectangle.getWidth()),
                () -> assertEquals(Double.parseDouble(height), actualRectangle.getHeight())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "3",
            "3,4,5"
    })
    @DisplayName("Должен выбрасывать исключение при неверном количестве параметров")
    void shouldThrowExceptionWhenParamsCountIsInvalid(String rawParams) {
        // Arrange
        String[] params = rawParams.split(",");

        // Act
        WrongParamCountException exception = assertThrows(
                WrongParamCountException.class,
                () -> factory.create(params)
        );

        // Assert
        assertEquals(
                Messages.WRONG_COUNT_OF_PARAM_EXCEPTION.formatted(
                        factory.getShapeType(),
                        factory.getParamsNeedCount()
                ),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при нечисловых параметрах")
    void shouldThrowExceptionWhenParamsAreNotNumbers() {
        // Arrange
        String[] params = {"a", "b"};

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(params)
        );

        // Assert
        assertEquals(
                Messages.VALUE_MUST_BE_NUMBER,
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при отрицательных значениях")
    void shouldThrowExceptionWhenParamsAreNegative() {
        // Arrange
        String[] params = {"-1", "5"};

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(params)
        );

        // Assert
        assertEquals(
                Messages.VALUE_MUST_BE_GREATER.formatted(ParserUtil.MIN_VALUE_EXCLUSIVE),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при нулевом значении стороны")
    void shouldThrowExceptionWhenAnySideIsZero() {
        // Arrange
        String[] params = {"0", "5"};

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(params)
        );

        // Assert
        assertEquals(
                Messages.VALUE_MUST_BE_GREATER.formatted(ParserUtil.MIN_VALUE_EXCLUSIVE),
                exception.getMessage()
        );
    }
}