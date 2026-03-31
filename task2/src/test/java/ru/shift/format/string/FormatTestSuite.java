package ru.shift.format.string;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CircleStringFormatterTest.class,
        RectangleStringFormatterTest.class,
        TriangleStringFormatterTest.class
})
public class FormatTestSuite {
}
