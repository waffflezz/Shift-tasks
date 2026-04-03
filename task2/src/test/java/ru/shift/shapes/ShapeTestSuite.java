package ru.shift.shapes;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CircleTest.class,
        RectangleTest.class,
        TriangleTest.class,
})
public class ShapeTestSuite {
    static final double STANDARD_DELTA = 1e-6;
}
