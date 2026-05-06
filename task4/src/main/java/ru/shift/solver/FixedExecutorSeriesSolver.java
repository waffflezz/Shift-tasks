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

/**
 * Солвер, вычисляющий сумму ряда через фиксированный пул потоков {@link Executors#newFixedThreadPool(int)}.
 */
public final class FixedExecutorSeriesSolver implements SeriesSolver {
    private final static String FIXED_EXECUTOR_SOLVER_NAME = "fixed-executor";

    private final int poolSize;
    private final int taskCount;
    private final TaskFactory taskFactory;

    /**
     * Создаёт солвер с фабрикой задач по умолчанию.
     *
     * @param poolSize размер пула потоков
     * @param taskCount количество создаваемых задач
     */
    public FixedExecutorSeriesSolver(int poolSize, int taskCount) {
        this(poolSize, taskCount, new SeriesTaskFactory());
    }

    /**
     * Создаёт солвер с настраиваемой фабрикой задач.
     *
     * @param poolSize размер пула потоков
     * @param taskCount количество создаваемых задач
     * @param taskFactory фабрика задач для разбиения диапазона
     */
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

        double result = 0.0;

        if (termsCount == 0) {
            return result;
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
            return result;
        }

        try (ExecutorService executorService = Executors.newFixedThreadPool(poolSize)) {
            List<Future<Double>> futures = executorService.invokeAll(tasks);

            try {
                for (Future<Double> future : futures) {
                    result += future.get();
                }
            } catch (InterruptedException exception) {
                throw new IllegalStateException("Series calculation was interrupted", exception);
            } catch (ExecutionException exception) {
                throw new IllegalStateException("Series calculation failed", exception);
            }

            return result;
        } catch (InterruptedException exception) {
            throw new IllegalStateException("Series calculation was interrupted", exception);
        }
    }
}
