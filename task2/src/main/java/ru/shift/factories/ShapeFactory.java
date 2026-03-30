package ru.shift.factories;

import ru.shift.shapes.Shape;
import ru.shift.shapes.types.ShapeType;

/**
 * Интерфейс фабрики для создания геометрических фигур.
 *
 * <p>Определяет контракт для создания конкретного типа {@link Shape}
 * на основе входных параметров (например, считанных из строки).</p>
 *
 * @param <S> тип создаваемой фигуры
 */
public interface ShapeFactory<S extends Shape> {
    /**
     * Возвращает тип фигуры, создаваемой данной фабрикой.
     *
     * @return тип фигуры ({@link ShapeType})
     */
    ShapeType getShapeType();

    /**
     * Создаёт экземпляр фигуры на основе переданных параметров.
     *
     * <p>Параметры передаются в виде массива строк и должны быть
     * предварительно валидированы.</p>
     *
     * @param params параметры для создания фигуры
     * @return созданная фигура
     * @throws IllegalArgumentException если параметры некорректны
     */
    S create(String[] params);

    /**
     * Возвращает количество параметров, необходимых для создания фигуры.
     *
     * @return требуемое количество параметров
     */
    int getParamsNeedCount();
}
