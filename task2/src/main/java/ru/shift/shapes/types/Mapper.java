package ru.shift.shapes.types;

import ru.shift.exceptions.UnknownShapeTypeException;

/**
 * Утилитный класс для преобразования строковых значений
 * в типы геометрических фигур ({@link ShapeType}).
 */
public class Mapper {
    /**
     * Преобразует строковое представление типа фигуры в {@link ShapeType}.
     *
     * <p>Ожидается, что входная строка строго соответствует имени
     * одного из значений перечисления {@link ShapeType} (например, "CIRCLE").</p>
     *
     * @param stringShapeType строковое представление типа фигуры
     * @return соответствующий {@link ShapeType}
     * @throws UnknownShapeTypeException если переданное значение не соответствует ни одному типу фигуры
     */
    public static ShapeType fromStringToShapeType(String stringShapeType) {
        return switch (stringShapeType) {
            case "CIRCLE" -> ShapeType.CIRCLE;
            case "RECTANGLE" -> ShapeType.RECTANGLE;
            case "TRIANGLE" -> ShapeType.TRIANGLE;
            default -> throw new UnknownShapeTypeException(stringShapeType);
        };
    }
}
