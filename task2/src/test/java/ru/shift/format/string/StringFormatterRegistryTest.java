package ru.shift.format.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shift.format.ShapeFormatter;
import ru.shift.shapes.Circle;
import ru.shift.shapes.Rectangle;
import ru.shift.shapes.Triangle;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StringFormatterRegistryTest {

    private final StringFormatterRegistry registry = new StringFormatterRegistry();

    @Test
    @DisplayName("Должен возвращать formatter для Circle, загруженный через ServiceLoader")
    void shouldReturnFormatterForCircle() {
        Circle circle = new Circle(5.0);

        ShapeFormatter<Circle, String> formatter = registry.getFormatter(circle);

        assertNotNull(formatter);
        assertInstanceOf(CircleStringFormatter.class, formatter);
    }

    @Test
    @DisplayName("Должен возвращать formatter для Rectangle, загруженный через ServiceLoader")
    void shouldReturnFormatterForRectangle() {
        Rectangle rectangle = new Rectangle(4.0, 6.0);

        ShapeFormatter<Rectangle, String> formatter = registry.getFormatter(rectangle);

        assertNotNull(formatter);
        assertInstanceOf(RectangleStringFormatter.class, formatter);
    }

    @Test
    @DisplayName("Должен возвращать formatter для Triangle, загруженный через ServiceLoader")
    void shouldReturnFormatterForTriangle() {
        Triangle triangle = new Triangle(3.0, 4.0, 5.0);

        ShapeFormatter<Triangle, String> formatter = registry.getFormatter(triangle);

        assertNotNull(formatter);
        assertInstanceOf(TriangleStringFormatter.class, formatter);
    }
}