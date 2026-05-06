package ru.shift.solver;

import ru.shift.series.Series;
import ru.shift.task.SeriesTask;
import ru.shift.task.SeriesTaskFactory;
import ru.shift.task.TaskFactory;
import java.util.List;

/**
 * Солвер, вычисляющий сумму ряда в одном потоке.
 */
public final class SingleThreadSeriesSolver implements SeriesSolver {
    private static final String SINGLE_THREAD_SOLVER_NAME = "single-thread";

    private final TaskFactory taskFactory;

    /**
     * Создаёт однопоточный солвер с фабрикой задач по умолчанию.
     */
    public SingleThreadSeriesSolver() {
        this(new SeriesTaskFactory());
    }

    /**
     * Создаёт однопоточный солвер с указанной фабрикой задач.
     *
     * @param taskFactory фабрика задач для построения вычислительной задачи
     */
    public SingleThreadSeriesSolver(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    @Override
    public String name() {
        return SINGLE_THREAD_SOLVER_NAME;
    }

    @Override
    public double calculate(Series series, long termsCount) {
        final int taskCount = 1;

        SeriesSolverValidator.validate(series, termsCount);

        long start = series.firstIndex();
        long end = start + termsCount;
        List<SeriesTask> tasks = taskFactory.createTasks(series.seriesFunction(), start, end, taskCount);
        if (tasks.isEmpty()) {
            return 0.0;
        }

        SeriesTask task = tasks.getFirst();
        task.call();

        return task.getResult();
    }
}
