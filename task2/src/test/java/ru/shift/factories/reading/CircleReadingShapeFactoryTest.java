package ru.shift.factories.reading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shift.constants.Messages;
import ru.shift.exceptions.ParseDoubleException;
import ru.shift.io.InputReader;
import ru.shift.shapes.Circle;
import ru.shift.utils.ParserUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CircleReadingShapeFactoryTest {

    private final CircleReadingShapeFactory factory = new CircleReadingShapeFactory();

    @Mock
    private InputReader reader;

    @Test
    @DisplayName("Должен возвращать тип CIRCLE")
    void shouldReturnCircleType() {
        // Arrange

        // Act
        String actualShapeType = factory.getShapeType();

        // Assert
        assertEquals("CIRCLE", actualShapeType);
    }

    @Test
    @DisplayName("Должен возвращать нужное количество параметров")
    void shouldReturnParamsNeedCount() {
        // Arrange

        // Act
        int actualParamsNeedCount = factory.getParamsNeedCount();

        // Assert
        assertEquals(1, actualParamsNeedCount);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2.5", "10"})
    @DisplayName("Должен корректно создавать окружность")
    void shouldCreateCircle(String radius) throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn(radius);

        // Act
        Circle actualCircle = factory.create(reader);

        // Assert
        assertEquals(Double.parseDouble(radius), actualCircle.getRadius());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1 2",
            "1 2 3"
    })
    @DisplayName("Должен выбрасывать исключение при неверном количестве параметров")
    void shouldThrowExceptionWhenParamsCountIsInvalid(String rawParams) throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn(rawParams);

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
    @DisplayName("Должен выбрасывать исключение пустой строки при отсутствии параметров")
    void shouldThrowExceptionWhenParamsStringIsEmpty() throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn("");

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(reader)
        );

        assertEquals(
                Messages.STRING_IS_BLANK,
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при нечисловом параметре")
    void shouldThrowExceptionWhenParamIsNotNumber() throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn("abc");

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
    @DisplayName("Должен выбрасывать исключение при отрицательном радиусе")
    void shouldThrowExceptionWhenRadiusIsNegative() throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn("-1");

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
    @DisplayName("Должен выбрасывать исключение при нулевом радиусе")
    void shouldThrowExceptionWhenRadiusIsZero() throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn("0");

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