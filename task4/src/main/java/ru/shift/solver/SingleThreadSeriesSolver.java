package ru.shift.solver;

import ru.shift.series.Series;
import ru.shift.task.SeriesTask;
import ru.shift.task.SeriesTaskFactory;
import ru.shift.task.TaskFactory;
import java.util.List;

public final class SingleThreadSeriesSolver implements SeriesSolver {
    private static final String SINGLE_THREAD_SOLVER_NAME = "single-thread";

    private final TaskFactory taskFactory;

    public SingleThreadSeriesSolver() {
        this(new SeriesTaskFactory());
    }

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
