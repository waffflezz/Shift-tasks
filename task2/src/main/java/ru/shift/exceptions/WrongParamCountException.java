package ru.shift.exceptions;

import ru.shift.constants.Messages;
import ru.shift.shapes.types.ShapeType;

/**
 * Исключение, выбрасываемое при некорректном количестве параметров
 * при создании геометрической фигуры.
 */
public class WrongParamCountException extends IllegalArgumentException {
    /**
     * Создаёт исключение с сообщением об ошибке,
     * содержащим тип фигуры и ожидаемое количество параметров.
     *
     * @param shapeType тип фигуры
     * @param needParams ожидаемое количество параметров
     */
    public WrongParamCountException(ShapeType shapeType, int needParams) {
        super(Messages.WRONG_COUNT_OF_PARAM_EXCEPTION.formatted(shapeType, needParams));
    }
}
