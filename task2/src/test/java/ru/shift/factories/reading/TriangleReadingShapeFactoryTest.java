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
import ru.shift.exceptions.triangle.TriangleCantExistsException;
import ru.shift.io.InputReader;
import ru.shift.shapes.Triangle;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TriangleReadingShapeFactoryTest {

    private final TriangleReadingShapeFactory factory = new TriangleReadingShapeFactory();

    @Mock
    private InputReader reader;

    @Test
    @DisplayName("Должен возвращать тип TRIANGLE")
    void shouldReturnTriangleType() {
        // Arrange

        // Act
        String type = factory.getShapeType();

        // Assert
        assertEquals("TRIANGLE", type);
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
    void shouldCreateTriangle(double a, double b, double c) throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn(a + " " + b + " " + c);

        // Act
        Triangle triangle = factory.create(reader);

        // Assert
        assertAll(
                () -> assertEquals(a, triangle.getSideA()),
                () -> assertEquals(b, triangle.getSideB()),
                () -> assertEquals(c, triangle.getSideC())
        );
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при недостаточном количестве параметров")
    void shouldThrowExceptionWhenParamsCountInvalid() throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn("3 4");

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
        when(reader.readLine()).thenReturn("");

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
    @DisplayName("Должен выбрасывать исключение при невалидных числах")
    void shouldThrowExceptionWhenParamsNotNumbers() throws IOException {
        // Arrange
        when(reader.readLine()).thenReturn("a b c");

        // Act
        ParseDoubleException exception = assertThrows(
                ParseDoubleException.class,
                () -> factory.create(reader)
        );

        // Assert
        assertEquals(Messages.VALUE_MUST_BE_NUMBER, exception.getMessage());
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при невозможном треугольнике")
    void shouldThrowExceptionWhenTriangleInvalid() throws IOException {
        // Arrange
        double sideA = 1;
        double sideB = 2;
        double sideC = 3;

        when(reader.readLine()).thenReturn(sideA + " " + sideB + " " + sideC);

        // Act
        TriangleCantExistsException exception = assertThrows(
                TriangleCantExistsException.class,
                () -> factory.create(reader)
        );

        // Assert
        assertEquals(
                Messages.TRIANGLE_CANT_EXISTS_EXCEPTION.formatted(sideA, sideB, sideC),
                exception.getMessage()
        );
    }
}