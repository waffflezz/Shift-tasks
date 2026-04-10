package ru.shift.exceptions;

/**
 * Исключение, выбрасываемое при ошибке преобразования строки
 * в число типа {@code double}.
 */
public class ParseDoubleException extends IllegalArgumentException {
    /**
     * Создаёт исключение с описанием причины ошибки.
     *
     * @param reason описание причины ошибки
     */
    public ParseDoubleException(String reason) {
        super(reason);
    }
}
