package ru.shift.factories.reading;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReadingShapeFactoryProviderTest {

    private final ReadingShapeFactoryProvider provider = new ReadingShapeFactoryProvider();

    @Test
    @DisplayName("Должен возвращать фабрику для CIRCLE")
    void shouldReturnFactoryForCircle() {
        // Arrange

        // Act
        Optional<ReadingShapeFactory<?>> actualFactory = provider.getFactory("CIRCLE");

        // Assert
        assertTrue(actualFactory.isPresent());
        assertInstanceOf(CircleReadingShapeFactory.class, actualFactory.get());
    }

    @Test
    @DisplayName("Должен возвращать фабрику для RECTANGLE")
    void shouldReturnFactoryForRectangle() {
        // Arrange

        // Act
        Optional<ReadingShapeFactory<?>> actualFactory = provider.getFactory("RECTANGLE");

        // Assert
        assertTrue(actualFactory.isPresent());
        assertInstanceOf(RectangleReadingShapeFactory.class, actualFactory.get());
    }

    @Test
    @DisplayName("Должен возвращать фабрику для TRIANGLE")
    void shouldReturnFactoryForTriangle() {
        // Arrange

        // Act
        Optional<ReadingShapeFactory<?>> actualFactory = provider.getFactory("TRIANGLE");

        // Assert
        assertTrue(actualFactory.isPresent());
        assertInstanceOf(TriangleReadingShapeFactory.class, actualFactory.get());
    }

    @Test
    @DisplayName("Должен возвращать пустой Optional для неизвестного типа фигуры")
    void shouldReturnEmptyOptionalForUnknownShapeType() {
        // Arrange

        // Act
        Optional<ReadingShapeFactory<?>> actualFactory = provider.getFactory("UNKNOWN_SHAPE");

        // Assert
        assertTrue(actualFactory.isEmpty());
    }

    @Test
    @DisplayName("Должен возвращать длину самого длинного типа фигуры с учетом перевода строки")
    void shouldReturnMaxShapeTypeLength() {
        // Arrange
        int expectedMaxLength = "RECTANGLE".length() + System.lineSeparator().length();

        // Act
        int actualMaxLength = provider.getMaxShapeTypeLength();

        // Assert
        assertEquals(expectedMaxLength, actualMaxLength);
    }
}