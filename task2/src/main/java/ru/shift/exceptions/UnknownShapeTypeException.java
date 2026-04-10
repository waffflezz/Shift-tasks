package ru.shift.exceptions;

import ru.shift.constants.Messages;

/**
 * Исключение, выбрасываемое при попытке обработать
 * неизвестный тип геометрической фигуры.
 */
public class UnknownShapeTypeException extends IllegalArgumentException {
    /**
     * Создаёт исключение с сообщением об ошибке,
     * содержащим неизвестный тип фигуры.
     *
     * @param unknownShapeType строковое представление неизвестного типа
     */
    public UnknownShapeTypeException(String unknownShapeType) {
        super(Messages.UNKNOWN_SHAPE_TYPE.formatted(unknownShapeType));
    }
}
