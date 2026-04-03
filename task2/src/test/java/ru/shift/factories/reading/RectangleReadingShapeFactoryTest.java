package ru.shift.factories.reading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shift.constants.Messages;
import ru.shift.exceptions.BlankParamException;
import ru.shift.exceptions.ParseDoubleException;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.io.InputReader;
import ru.shift.shapes.Rectangle;
import ru.shift.utils.ParserUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RectangleReadingShapeFactoryTest {

    private final RectangleReadingShapeFactory factory = new RectangleReadingShapeFactory();

    @Mock
    private InputReader reader;

    @Test
    @DisplayName("Должен возвращать тип RECTANGLE")
    void shouldReturnRectangleType() {
        // Arrange

        // Act
        String actualShapeType = factory.getShapeType();

        // Assert
        assertEquals("RECTANGLE", actualShapeType);
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
    void shouldCreateRectangle(double width, double height) throws IOException {
        // Arrange
        when(reader.readLine(anyInt()))
                .thenReturn(width + " " + height);

        // Act
        Rectangle actualRectangle = factory.create(reader);

        // Assert
        assertAll(
                () -> assertEquals(width, actualRectangle.getWidth()),
                () -> assertEquals(height, actualRectangle.getHeight())
        );
    }

    @ParameterizedTest
    @CsvSource({
            "'3'",
            "'3 4 5'"
    })
    @DisplayName("Должен выбрасывать исключение при неверном количестве параметров")
    void shouldThrowExceptionWhenParamsCountIsInvalid(String rawParams) throws IOException {
        // Arrange
        when(reader.readLine(anyInt()))
                .thenReturn(rawParams);

        // Act
        WrongParamCountException exception = assertThrows(
                WrongParamCountException.class,
                () -> factory.create(reader)
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
    @DisplayName("Должен выбрасывать исключение пустой строки при отсутствии параметров")
    void shouldThrowExceptionWhenParamsStringIsEmpty() throws IOException {
        // Arrange
        when(reader.readLine(anyInt())).thenReturn("");

        // Act
        BlankParamException exception = assertThrows(
                BlankParamException.class,
                () -> factory.create(reader)
        );

        assertEquals(
                Messages.STRING_IS_BLANK,
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при нечисловых параметрах")
    void shouldThrowExceptionWhenParamsAreNotNumbers() throws IOException {
        // Arrange
        when(reader.readLine(anyInt()))
                .thenReturn("a b");

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(reader)
        );

        // Assert
        assertEquals(
                Messages.VALUE_MUST_BE_NUMBER,
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при отрицательных значениях")
    void shouldThrowExceptionWhenParamsAreNegative() throws IOException {
        // Arrange
        when(reader.readLine(anyInt()))
                .thenReturn("-1 5");

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(reader)
        );

        // Assert
        assertEquals(
                Messages.VALUE_MUST_BE_GREATER.formatted(ParserUtil.MIN_VALUE_EXCLUSIVE),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при нулевом значении стороны")
    void shouldThrowExceptionWhenAnySideIsZero() throws IOException {
        // Arrange
        when(reader.readLine(anyInt()))
                .thenReturn("0 5");

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(reader)
        );

        // Assert
        assertEquals(
                Messages.VALUE_MUST_BE_GREATER.formatted(ParserUtil.MIN_VALUE_EXCLUSIVE),
                exception.getMessage()
        );
    }
}