package ru.shift.task;

import java.util.List;
import java.util.function.LongToDoubleFunction;

public interface TaskFactory {
    List<SeriesTask> createTasks(
            LongToDoubleFunction function,
            long startInclusive,
            long endExclusive,
            int taskCount
    );
}
