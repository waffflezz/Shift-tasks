package ru.shift.constants;

import java.text.DecimalFormat;

/**
 * Класс, содержащий константы,
 * используемые при работе с геометрическими фигурами
 * и их форматированием.
 */
public class ShapeConstants {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    public static final String UNITS = " см";
    public static final String SQUARE = " кв." + UNITS;
    public static final String DEGREES = "°";
}
