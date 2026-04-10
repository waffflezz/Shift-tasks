package ru.shift;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import ru.shift.factories.reading.ReadingFactoryTestSuite;
import ru.shift.format.string.FormatTestSuite;
import ru.shift.shapes.ShapeTestSuite;
import ru.shift.utils.UtilsTestSuite;

@Suite
@SelectClasses(value = {
        ReadingFactoryTestSuite.class,
        FormatTestSuite.class,
        ShapeTestSuite.class,
        UtilsTestSuite.class

})
public class AllTests {
}
