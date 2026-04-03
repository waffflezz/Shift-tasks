package ru.shift.factories;

import ru.shift.exceptions.BlankParamException;
import ru.shift.exceptions.WrongParamCountException;

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
        if (params.length == needParamsCount) return;
        throw new WrongParamCountException(shapeType, needParamsCount);
    }

    public static void validateBlankLine(String line) {
        if (!line.isBlank()) return;
        throw new BlankParamException();
    }
}
