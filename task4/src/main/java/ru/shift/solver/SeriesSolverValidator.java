package ru.shift.solver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.shift.series.Series;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class SeriesSolverValidator {
    static void validate(Series series, long termsCount) {
        Objects.requireNonNull(series, "series не должен быть null");

        if (termsCount < 0) {
            throw new IllegalArgumentException("termsCount должен быть положительным числом");
        }
    }
}
