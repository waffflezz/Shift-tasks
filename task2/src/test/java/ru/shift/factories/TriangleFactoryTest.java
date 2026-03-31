package ru.shift.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import ru.shift.constants.Messages;
import ru.shift.exceptions.ParseDoubleException;
import ru.shift.exceptions.WrongParamCountException;
import ru.shift.exceptions.triangle.TriangleCantExistsException;
import ru.shift.shapes.Triangle;
import ru.shift.shapes.types.ShapeType;

import static org.junit.jupiter.api.Assertions.*;

class TriangleFactoryTest {

    private final TriangleFactory factory = new TriangleFactory();

    @Test
    @DisplayName("Должен возвращать тип TRIANGLE")
    void shouldReturnTriangleType() {
        // Arrange

        // Act
        ShapeType type = factory.getShapeType();

        // Assert
        assertEquals(ShapeType.TRIANGLE, type);
    }

    @Test
    @DisplayName("Должен возвращать нужное количество параметров")
    void shouldReturnParamsCount() {
        // Arrange

        // Act
        int count = factory.getParamsNeedCount();

        // Assert
        assertEquals(3, count);
    }

    @ParameterizedTest
    @CsvSource({
            "3,4,5",
            "5,5,6",
            "7.5,8.5,9.5"
    })
    @DisplayName("Должен корректно создавать треугольник")
    void shouldCreateTriangle(String a, String b, String c) {
        // Arrange
        String[] params = {a, b, c};

        // Act
        Triangle triangle = factory.create(params);

        // Assert
        assertAll(
                () -> assertEquals(Double.parseDouble(a), triangle.getSideA()),
                () -> assertEquals(Double.parseDouble(b), triangle.getSideB()),
                () -> assertEquals(Double.parseDouble(c), triangle.getSideC())
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при недостаточном количестве параметров")
    void shouldThrowExceptionWhenParamsCountInvalid() {
        // Arrange
        String[] params = {"3", "4"};

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
    @DisplayName("Должен выбрасывать исключение при невалидных числах")
    void shouldThrowExceptionWhenParamsNotNumbers() {
        // Arrange
        String[] params = {"a", "b", "c"};

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
    @DisplayName("Должен выбрасывать исключение при невозможном треугольнике")
    void shouldThrowExceptionWhenTriangleInvalid() {
        // Arrange
        double sideA = 1;
        double sideB = 2;
        double sideC = 3;
        String[] params = {
                String.valueOf(sideA),
                String.valueOf(sideB),
                String.valueOf(sideC)
        };

        // Act
        TriangleCantExistsException exception = assertThrows(
                TriangleCantExistsException.class,
                () -> factory.create(params)
        );

        // Assert
        assertEquals(
                Messages.TRIANGLE_CANT_EXISTS_EXCEPTION.formatted(sideA, sideB, sideC),
                exception.getMessage()
        );
    }
}