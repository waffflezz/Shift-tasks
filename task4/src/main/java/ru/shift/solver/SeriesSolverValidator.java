package ru.shift.solver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.shift.series.Series;

import java.util.Objects;

/**
 * Выполняет базовую проверку аргументов для реализаций {@link SeriesSolver}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class SeriesSolverValidator {
    /**
     * Проверяет корректность ряда и числа суммируемых членов.
     *
     * @param series описание ряда
     * @param termsCount количество членов ряда
     */
    static void validate(Series series, long termsCount) {
        Objects.requireNonNull(series, "series don't be null");

        if (termsCount < 0) {
            throw new IllegalArgumentException("termsCount must be positive number");
        }
    }
}
