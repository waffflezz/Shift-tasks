package ru.shift.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.shift.constants.Messages;
import ru.shift.exceptions.ParseDoubleException;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.shapes.Circle;
import ru.shift.shapes.types.ShapeType;
import ru.shift.utils.ParserUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircleFactoryTest {
    private final CircleFactory factory = new CircleFactory();

    @Test
    @DisplayName("Должен возвращать тип CIRCLE")
    void shouldReturnCircleType() {
        // Arrange

        // Act
        ShapeType actualShapeType = factory.getShapeType();

        // Assert
        assertEquals(ShapeType.CIRCLE, actualShapeType);
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
    void shouldCreateCircle(String radius) {
        // Arrange
        String[] params = {radius};

        // Act
        Circle actualCircle = factory.create(params);

        // Assert
        assertEquals(Double.parseDouble(radius), actualCircle.getRadius());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "1,2"
    })
    @DisplayName("Должен выбрасывать исключение при неверном количестве параметров")
    void shouldThrowExceptionWhenParamsCountIsInvalid(String rawParams) {
        // Arrange
        String[] params = rawParams.isEmpty() ? new String[0] : rawParams.split(",");

        // Act
        WrongParamCountException exception = assertThrows(
                WrongParamCountException.class,
                () -> factory.create(params)
        );

        // Assert
        assertEquals(
                Messages.WRONG_COUNT_OF_PARAM_EXCEPTION.formatted(
                        ShapeType.CIRCLE,
                        factory.getParamsNeedCount()
                ),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при нечисловом параметре")
    void shouldThrowExceptionWhenParamIsNotNumber() {
        // Arrange
        String[] params = {"abc"};

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
    @DisplayName("Должен выбрасывать исключение при отрицательном радиусе")
    void shouldThrowExceptionWhenRadiusIsNegative() {
        // Arrange
        String[] params = {"-1"};

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
    @DisplayName("Должен выбрасывать исключение при нулевом радиусе")
    void shouldThrowExceptionWhenRadiusIsZero() {
        // Arrange
        String[] params = {"0"};

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