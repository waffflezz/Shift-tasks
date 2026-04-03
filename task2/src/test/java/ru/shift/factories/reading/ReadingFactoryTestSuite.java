package ru.shift.factories.reading;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CircleReadingShapeFactoryTest.class,
        RectangleReadingShapeFactoryTest.class,
        TriangleReadingShapeFactoryTest.class,
        ReadingShapeFactoryProviderTest.class,
        FactoriesValidatorTest.class
})
public class ReadingFactoryTestSuite {
}
