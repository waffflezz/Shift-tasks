package ru.shift.exceptions;

import ru.shift.constants.Messages;
import ru.shift.constants.TableConstants;

/**
 * Исключение {@code InvalidTableSizeException} выбрасывается в случае,
 * если переданный размер таблицы умножения выходит за допустимые пределы.
 * <p>
 * Сообщение об ошибке формируется на основе переданного значения
 * и содержит информацию о корректном диапазоне значений.
 * </p>
 *
 * <p>
 * Наследуется от {@link IllegalArgumentException}, так как возникает
 * при передаче некорректного аргумента в метод или конструктор.
 * </p>
 */
public class InvalidTableSizeException extends IllegalArgumentException {
    /**
     * Создаёт исключение с сообщением об ошибке, сформированным
     * на основе некорректного значения размера таблицы.
     *
     * @param tableSize некорректный размер таблицы умножения
     */
    public InvalidTableSizeException(int tableSize) {
        super(Messages.INVALID_TABLE_SIZE.formatted(tableSize, TableConstants.LEFT_BOUND, TableConstants.RIGHT_BOUND));
    }
}
