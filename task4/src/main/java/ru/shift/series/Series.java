package ru.shift.series;

import java.util.function.LongToDoubleFunction;

public record Series(
        String code,
        String name,
        String formula,
        long firstIndex,
        LongToDoubleFunction seriesFunction,
        Double expectedInfiniteSum
) {}
