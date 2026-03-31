package ru.shift.factories;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CircleFactoryTest.class,
        FactoriesValidatorTest.class,
        FactoryRegistryTest.class,
        RectangleFactoryTest.class,
        TriangleFactoryTest.class
})
public class FactoryTestSuite {
}
