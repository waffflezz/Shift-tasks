package ru.shift.factories;

import ru.shift.shapes.types.ShapeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реестр фабрик для создания геометрических фигур.
 *
 * <p>Хранит соответствие между {@link ShapeType} и реализациями {@link ShapeFactory}.</p>
 *
 * <p>Используется для получения подходящей фабрики по типу фигуры.</p>
 */
public class FactoryRegistry {
    private static final Map<ShapeType, ShapeFactory<?>> factories = new HashMap<>();

    /**
     * Регистрирует фабрику для определённого типа фигуры.
     *
     * @param shapeFactory фабрика, создающая фигуры
     */
    public static void registerFactory(ShapeFactory<?> shapeFactory) {
        factories.put(shapeFactory.getShapeType(), shapeFactory);
    }

    /**
     * Возвращает фабрику для указанного типа фигуры.
     *
     * <p>Если фабрика для переданного {@link ShapeType} не зарегистрирована,
     * возвращается {@link Optional#empty()}.</p>
     *
     * @param shapeType тип фигуры
     * @return {@link Optional}, содержащий фабрику, либо пустой {@link Optional},
     * если фабрика не найдена
     */
    public static Optional<ShapeFactory<?>> getFactory(ShapeType shapeType) {
        return Optional.ofNullable(factories.get(shapeType));
    }
}
