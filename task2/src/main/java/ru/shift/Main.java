package ru.shift;

import ru.shift.shapes.Circle;
import ru.shift.shapes.Rectangle;
import ru.shift.shapes.Shape;
import ru.shift.shapes.Triangle;

public class Main {
    public static void main(String[] args) {
        Shape circle = new Rectangle(3, 5);
        System.out.println(circle.getShapeStringData());
    }
}