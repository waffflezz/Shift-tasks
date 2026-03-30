package ru.shift.factories;

import ru.shift.shapes.types.ShapeType;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {
    private static final Map<ShapeType, ShapeFactory<?>> factories = new HashMap<>();

    public static void registerFactory(ShapeFactory<?> shapeFactory) {
        factories.put(shapeFactory.getShapeType(), shapeFactory);
    }

    public static ShapeFactory<?> getFactory(ShapeType shapeType) {
        return factories.get(shapeType);
    }
}
