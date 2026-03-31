package ru.shift.factories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shift.shapes.Circle;
import ru.shift.shapes.Triangle;
import ru.shift.shapes.types.ShapeType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FactoryRegistryTest {

    @Mock
    private ShapeFactory<Circle> circleFactory;

    @Mock
    private ShapeFactory<Triangle> triangleFactory;


    @Test
    @DisplayName("Должен регистрировать и возвращать фабрику по типу")
    void shouldRegisterAndReturnFactory() {
        // Arrange
        when(circleFactory.getShapeType()).thenReturn(ShapeType.CIRCLE);

        FactoryRegistry.registerFactory(circleFactory);

        // Act
        Optional<ShapeFactory<?>> result = FactoryRegistry.getFactory(ShapeType.CIRCLE);

        // Assert
        assertTrue(result.isPresent());
        assertSame(circleFactory, result.get());
    }

    @Test
    @DisplayName("Должен возвращать пустой Optional, если фабрика не найдена")
    void shouldReturnEmptyOptionalWhenFactoryNotFound() {
        // Arrange

        // Act
        Optional<ShapeFactory<?>> result = FactoryRegistry.getFactory(ShapeType.RECTANGLE);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Должен перезаписывать фабрику при повторной регистрации для того же типа")
    void shouldOverrideFactoryWhenRegisteringSameType() {
        // Arrange
        when(circleFactory.getShapeType()).thenReturn(ShapeType.TRIANGLE);
        when(triangleFactory.getShapeType()).thenReturn(ShapeType.TRIANGLE);

        FactoryRegistry.registerFactory(circleFactory);
        FactoryRegistry.registerFactory(triangleFactory);

        // Act
        Optional<ShapeFactory<?>> result = FactoryRegistry.getFactory(ShapeType.TRIANGLE);

        // Assert
        assertTrue(result.isPresent());
        assertSame(triangleFactory, result.get());
    }
}