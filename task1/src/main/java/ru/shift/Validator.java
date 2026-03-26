package ru.shift;

public class Validator {
    private static final int LEFT_BOUND = 1;
    private static final int RIGHT_BOUND = 32;

    public static void validateTableSize(int tableSize, String reason) {
        if (tableSize < LEFT_BOUND || tableSize > RIGHT_BOUND) {
            throw new IllegalArgumentException(reason);
        }
    }
}
