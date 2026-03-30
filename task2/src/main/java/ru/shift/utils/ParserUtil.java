package ru.shift.utils;

import lombok.extern.slf4j.Slf4j;
import ru.shift.constants.Messages;
import ru.shift.exceptions.ParseDoubleException;

@Slf4j
public class ParserUtil {
    private static final int MIN_VALUE_EXCLUSIVE = 0;

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
            return value;
        } catch (NumberFormatException e) {
            throw new ParseDoubleException(Messages.VALUE_MUST_BE_NUMBER);
        }
    }
}
