package ru.shift.factories.reading;

import ru.shift.factories.ShapeFactory;
import ru.shift.factories.ShapeFactoryProvider;
import ru.shift.shapes.Shape;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * Провайдер фабрик для создания геометрических фигур.
 *
 * <p>Хранит соответствие между {@link Shape} в строковом представлении и реализациями {@link ShapeFactory}.</p>
 *
 * <p>Используется для получения подходящей фабрики по типу фигуры.</p>
 */
public class ReadingShapeFactoryProvider implements ShapeFactoryProvider<ReadingShapeFactory<?>> {
    private final Map<String, ReadingShapeFactory<?>> factoryMap = new HashMap<>();

    public ReadingShapeFactoryProvider() {
        ServiceLoader.load(ReadingShapeFactory.class).forEach(f -> factoryMap.put(f.getShapeType(), f));
    }

    public int getMaxShapeTypeLength() {
        return factoryMap.values().stream()
                .map(ReadingShapeFactory::getShapeType)
                .mapToInt(String::length)
                .max()
                .orElse(0) + System.lineSeparator().length();
    }

    @Override
    public Optional<ReadingShapeFactory<?>> getFactory(String shapeType) {
        return Optional.ofNullable(factoryMap.get(shapeType));
    }
}

