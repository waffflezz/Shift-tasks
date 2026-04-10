package ru.shift.utils;

import lombok.extern.slf4j.Slf4j;
import ru.shift.constants.Messages;
import ru.shift.exceptions.ParseDoubleException;

/**
 * Утилитный класс для парсинга числовых значений.
 */
@Slf4j
public class ParserUtil {
    public static final int MIN_VALUE_EXCLUSIVE = 0;

    /**
     * Преобразует строковое значение в положительное число типа {@code double}.
     *
     * <p>Метод выполняет следующие проверки:
     * <ul>
     *     <li>Строка не должна быть {@code null} или пустой</li>
     *     <li>Строка должна корректно преобразовываться в число</li>
     *     <li>Число должно быть строго больше {@link #MIN_VALUE_EXCLUSIVE}</li>
     * </ul>
     *
     * @param stringNumber строковое представление числа
     * @return положительное число типа {@code double}
     * @throws ParseDoubleException если:
     * <ul>
     *     <li>строка {@code null} или пустая</li>
     *     <li>строка не является числом</li>
     *     <li>число меньше или равно {@link #MIN_VALUE_EXCLUSIVE}</li>
     * </ul>
     */
    public static double parsePositiveDouble(String stringNumber) throws ParseDoubleException {
        log.trace("Преобразование числа: {}, в строку", stringNumber);
        if (stringNumber == null || stringNumber.isBlank()) {
            throw new ParseDoubleException(Messages.STRING_IS_BLANK);
        }
        try {
            double value = Double.parseDouble(stringNumber);
            if (value <= MIN_VALUE_EXCLUSIVE) {
                throw new ParseDoubleException(Messages.VALUE_MUST_BE_GREATER.formatted(MIN_VALUE_EXCLUSIVE));
            }

            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new ParseDoubleException(Messages.VALUE_MUST_BE_NUMBER);
            }

            return value;
        } catch (NumberFormatException e) {
            throw new ParseDoubleException(Messages.VALUE_MUST_BE_NUMBER);
        }
    }
}
