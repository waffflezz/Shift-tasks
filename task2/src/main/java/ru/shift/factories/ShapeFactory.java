package ru.shift.factories;

import ru.shift.shapes.Shape;

import java.io.IOException;

/**
 * Интерфейс фабрики для создания геометрических фигур.
 *
 * <p>Определяет контракт для создания конкретного типа {@link Shape}
 * на основе входных параметров (например, считанных из строки).</p>
 *
 * @param <S> тип создаваемой фигуры
 */
public interface ShapeFactory<S extends Shape, R> {
    /**
     * Возвращает тип фигуры, создаваемой данной фабрикой.
     *
     * @return тип фигуры
     */
    String getShapeType();

    /**
     * Создаёт экземпляр фигуры на основе переданных параметров.
     *
     * <p>Параметры передаются в виде массива строк и должны быть
     * предварительно валидированы.</p>
     *
     * @param reader ридер для парсинга и создания фигуры
     * @return созданная фигура
     * @throws IllegalArgumentException если параметры некорректны
     */
    S create(R reader) throws IOException;

    /**
     * Возвращает количество параметров, необходимых для создания фигуры.
     *
     * @return требуемое количество параметров
     */
    int getParamsNeedCount();
}
