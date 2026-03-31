package ru.shift.format.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shift.format.ShapeFormatter;
import ru.shift.shapes.Circle;
import ru.shift.shapes.Rectangle;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class StringFormatterRegistryTest {

    private final StringFormatterRegistry registry = new StringFormatterRegistry();

    @Mock
    private ShapeFormatter<Circle, String> circleFormatter;

    @Mock
    private ShapeFormatter<Rectangle, String> rectangleFormatter;

    @Test
    @DisplayName("Должен возвращать зарегистрированный mock formatter для Circle")
    void shouldReturnRegisteredMockFormatterForCircle() {
        // Arrange
        Circle circle = new Circle(5.0);
        StringFormatterRegistry.registerFormatter(Circle.class, circleFormatter);

        // Act
        ShapeFormatter<Circle, String> actualFormatter = registry.getFormatter(circle);

        // Assert
        assertSame(circleFormatter, actualFormatter);
    }

    @Test
    @DisplayName("Должен возвращать зарегистрированный mock formatter для Rectangle")
    void shouldReturnRegisteredMockFormatterForRectangle() {
        // Arrange
        Rectangle rectangle = new Rectangle(4.0, 6.0);
        StringFormatterRegistry.registerFormatter(Rectangle.class, rectangleFormatter);

        // Act
        ShapeFormatter<Rectangle, String> actualFormatter = registry.getFormatter(rectangle);

        // Assert
        assertSame(rectangleFormatter, actualFormatter);
    }
}