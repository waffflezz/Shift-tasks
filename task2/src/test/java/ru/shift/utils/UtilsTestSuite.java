package ru.shift.utils;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        FileUtilTest.class,
        ParserUtilTest.class
})
public class UtilsTestSuite {
}
