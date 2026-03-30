package ru.shift.utils;

public class Parser {
    public static double parsePositiveDouble(String str) throws IllegalArgumentException {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        try {
            double value = Double.parseDouble(str);
            if (value < 0) {
                throw new IllegalArgumentException("Значение должно быть больше 1");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Значение должно быть числом");
        }
    }
}
