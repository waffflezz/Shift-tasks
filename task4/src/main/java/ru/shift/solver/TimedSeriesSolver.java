package ru.shift.solver;

import lombok.extern.slf4j.Slf4j;
import ru.shift.series.Series;

/**
 * Декоратор солвера, измеряющий и логирующий время вычисления суммы ряда.
 */
@Slf4j
public final class TimedSeriesSolver implements SeriesSolver {
    private final SeriesSolver solver;

    /**
     * Создаёт декоратор над указанным солвером.
     *
     * @param solver солвер, выполнение которого нужно измерять
     */
    public TimedSeriesSolver(SeriesSolver solver) {
        this.solver = solver;
    }

    @Override
    public String name() {
        return solver.name();
    }

    @Override
    public double calculate(Series series, long termsCount) {
        long startedAt = System.currentTimeMillis();
        double sum = solver.calculate(series, termsCount);
        long elapsedMillis = System.currentTimeMillis() - startedAt;

        log.info(
                "{} calculated {} terms for '{}' in {} ms",
                solver.name(),
                termsCount,
                series.formula(),
                elapsedMillis
        );

        return sum;
    }
}
