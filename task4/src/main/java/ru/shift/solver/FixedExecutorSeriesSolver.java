package ru.shift.solver;

import ru.shift.series.Series;
import ru.shift.task.SeriesTask;
import ru.shift.task.SeriesTaskFactory;
import ru.shift.task.TaskFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class FixedExecutorSeriesSolver implements SeriesSolver {
    private final static String FIXED_EXECUTOR_SOLVER_NAME = "fixed-executor";

    private final int poolSize;
    private final int taskCount;
    private final TaskFactory taskFactory;

    public FixedExecutorSeriesSolver(int poolSize, int taskCount) {
        this(poolSize, taskCount, new SeriesTaskFactory());
    }

    public FixedExecutorSeriesSolver(int poolSize, int taskCount, TaskFactory taskFactory) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException("poolSize must be positive");
        }

        if (taskCount <= 0) {
            throw new IllegalArgumentException("taskCount must be positive");
        }

        this.poolSize = poolSize;
        this.taskCount = taskCount;
        this.taskFactory = taskFactory;
    }

    @Override
    public String name() {
        return FIXED_EXECUTOR_SOLVER_NAME;
    }

    @Override
    public double calculate(Series series, long termsCount) {
        SeriesSolverValidator.validate(series, termsCount);

        if (termsCount == 0) {
            return 0.0;
        }

        long start = series.firstIndex();
        long end = start + termsCount;
        List<SeriesTask> tasks = taskFactory.createTasks(
                series.seriesFunction(),
                start,
                end,
                taskCount
        );

        if (tasks.isEmpty()) {
            return 0.0;
        }

        try (ExecutorService executorService = Executors.newFixedThreadPool(poolSize)) {
            List<Future<Double>> futures = executorService.invokeAll(tasks);

            for (Future<Double> future : futures) {
                waitForResult(future);
            }

            return sumResults(tasks);
        } catch (InterruptedException exception) {
            throw new IllegalStateException("Series calculation was interrupted", exception);
        }
    }

    private void waitForResult(Future<Double> future) {
        try {
            future.get();
        } catch (InterruptedException exception) {
            throw new IllegalStateException("Series calculation was interrupted", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Series calculation failed", exception);
        }
    }

    private double sumResults(List<SeriesTask> tasks) {
        double sum = 0.0;

        for (SeriesTask task : tasks) {
            sum += task.getResult();
        }

        return sum;
    }
}
