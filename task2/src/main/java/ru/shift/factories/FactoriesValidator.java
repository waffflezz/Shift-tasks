package ru.shift.factories;

import ru.shift.exceptions.WrongParamCountException;
import ru.shift.shapes.types.ShapeType;

/**
 * Утилитный класс для валидации параметров,
 * используемых при создании геометрических фигур.
 */
public class FactoriesValidator {
    /**
     * Проверяет корректность количества переданных параметров.
     *
     * <p>Если фактическое количество параметров не совпадает с ожидаемым,
     * выбрасывается исключение {@link WrongParamCountException}.</p>
     *
     * @param params массив параметров
     * @param needParamsCount ожидаемое количество параметров
     * @param shapeType тип фигуры, для которой выполняется валидация
     * @throws WrongParamCountException если количество параметров некорректно
     */
    public static void validateParamsCount(Object[] params, int needParamsCount, String shapeType) {
        if (params.length != needParamsCount) {
            throw new WrongParamCountException(shapeType, needParamsCount);
        }
    }
}
