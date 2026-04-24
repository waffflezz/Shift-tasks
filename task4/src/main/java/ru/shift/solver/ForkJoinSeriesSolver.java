package ru.shift.solver;

import ru.shift.series.Series;
import ru.shift.task.ForkJoinSeriesTask;
import java.util.concurrent.ForkJoinPool;

/**
 * Солвер, вычисляющий сумму ряда с помощью {@link ForkJoinPool}.
 */
public final class ForkJoinSeriesSolver implements SeriesSolver {
    private static final String FORK_JOIN_SOLVER_NAME = "fork-join";

    private final int parallelism;
    private final long threshold;

    /**
     * Создаёт солвер на основе модели {@code fork-join}.
     *
     * @param parallelism уровень параллелизма пула
     * @param threshold порог разбиения задач на подзадачи
     */
    public ForkJoinSeriesSolver(int parallelism, long threshold) {
        if (parallelism <= 0) {
            throw new IllegalArgumentException("parallelism must be positive");
        }

        if (threshold <= 0) {
            throw new IllegalArgumentException("threshold must be positive");
        }

        this.parallelism = parallelism;
        this.threshold = threshold;
    }

    @Override
    public String name() {
        return FORK_JOIN_SOLVER_NAME;
    }

    @Override
    public double calculate(Series series, long termsCount) {
        final int startId = 1;

        SeriesSolverValidator.validate(series, termsCount);

        long start = series.firstIndex();
        long end = start + termsCount;
        try (ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism)) {
            return forkJoinPool.invoke(new ForkJoinSeriesTask(
                    startId,
                    series.seriesFunction(),
                    start,
                    end,
                    threshold
            ));
        }
    }
}
