package ru.shift.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsoleInput {
    public static int readInt(Scanner scanner) {
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }

            log.warn("Expected int value, but got '{}'", scanner.next());
        }

        throw new IllegalStateException("Input ended before int value was read");
    }

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
