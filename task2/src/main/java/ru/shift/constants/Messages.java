package ru.shift.constants;

public class Messages {
    public static final String SHAPE_TYPE = "Фигура: ";
    public static final String SHAPE_AREA = "Площадь: ";
    public static final String SHAPE_PERIMETER = "Периметр: ";

    public static final String CIRCLE_RADIUS = "Радиус: ";
    public static final String CIRCLE_DIAMETER = "Диаметр: ";

    public static final String RECTANGLE_DIAGONAL = "Диагональ: ";
    public static final String RECTANGLE_LENGTH = "Длина: ";
    public static final String RECTANGLE_WIDTH = "Ширина: ";

    public static final String TRIANGLE_SIDE_A = "Сторона A: ";
    public static final String TRIANGLE_SIDE_B = "Сторона B: ";
    public static final String TRIANGLE_SIDE_C = "Сторона C: ";
    public static final String TRIANGLE_OPPOSITE_ANGLE = ", противолежащий угол: ";

    public static final String UNKNOWN_SHAPE_TYPE = "Неизвестный тип фигуры: %s";

    public static final String TRIANGLE_SIDES_BELOW_ZERO_EXCEPTION = "Стороны должны быть больше нуля";
    public static final String TRIANGLE_CANT_EXISTS_EXCEPTION =
            "Треугольник со сторонами %.2f, %.2f, %.2f не существует";

    public static final String CIRCLE_RADIUS_BELOW_ZERO_EXCEPTION = "Радиус меньше 0";

    public static final String RECTANGLE_SIDES_BELOW_ZERO_EXCEPTION = "Радиус меньше 0";
}
