package ru.shift.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * Утилита для безопасного чтения числовых значений из консоли.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsoleInput {
    /**
     * Считывает из потока следующее целое число, пропуская некорректные.
     *
     * @param scanner источник входных данных
     * @return считанное целое число
     */
    public static int readInt(Scanner scanner) {
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }

            log.warn("Expected int value, but got '{}'", scanner.next());
        }

        throw new IllegalStateException("Input ended before int value was read");
    }

    /**
     * Считывает из потока следующее значение типа {@code long}, пропуская некорректные.
     *
     * @param scanner источник входных данных
     * @return считанное значение типа {@code long}
     */
    public static long readLong(Scanner scanner) {
        while (scanner.hasNext()) {
            if (scanner.hasNextLong()) {
                return scanner.nextLong();
            }

            log.warn("Expected long value, but got '{}'", scanner.next());
        }

        throw new IllegalStateException("Input ended before long value was read");
    }
}
