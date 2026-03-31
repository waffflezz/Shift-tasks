package ru.shift;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import ru.shift.factories.FactoryTestSuite;
import ru.shift.format.string.FormatTestSuite;
import ru.shift.shapes.ShapeTestSuite;
import ru.shift.utils.UtilsTestSuite;

@Suite
@SelectClasses(value = {
        FactoryTestSuite.class,
        FormatTestSuite.class,
        ShapeTestSuite.class,
        UtilsTestSuite.class

})
public class AllTests {
}
