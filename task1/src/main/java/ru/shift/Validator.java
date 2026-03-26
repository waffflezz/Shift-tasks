package ru.shift;

import ru.shift.constants.TableConstants;
import ru.shift.exceptions.InvalidTableSizeException;

/**
 * Класс {@code Validator}, предназначенный для проверки корректности
 * входных параметров, используемых при построении таблицы умножения.
 * <p>
 * В текущей реализации выполняет валидацию размера таблицы на соответствие
 * допустимому диапазону, заданному в {@link TableConstants}.
 * </p>
 */
public class Validator {
    /**
     * Проверяет корректность размера таблицы умножения.
     * <p>
     * Размер считается корректным, если он находится в диапазоне
     * от {@link TableConstants#LEFT_BOUND} до
     * {@link TableConstants#RIGHT_BOUND} включительно.
     * </p>
     *
     * @param tableSize размер таблицы умножения
     * @throws InvalidTableSizeException если {@code tableSize} выходит
     *                                   за допустимые пределы
     */
    public static void validateTableSize(int tableSize) {
        if (tableSize < TableConstants.LEFT_BOUND || tableSize > TableConstants.RIGHT_BOUND) {
            throw new InvalidTableSizeException(tableSize);
        }
    }
}
