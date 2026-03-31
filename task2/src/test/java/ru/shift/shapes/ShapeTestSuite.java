package ru.shift.shapes;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import ru.shift.shapes.types.MapperTest;

@Suite
@SelectClasses({
        CircleTest.class,
        RectangleTest.class,
        TriangleTest.class,
        MapperTest.class
})
public class ShapeTestSuite {
    static final double STANDARD_DELTA = 1e-6;
}
