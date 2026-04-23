package ru.shift.solver;

import ru.shift.series.Series;

public interface SeriesSolver {
    String name();

    double calculate(Series series, long termsCount);
}
