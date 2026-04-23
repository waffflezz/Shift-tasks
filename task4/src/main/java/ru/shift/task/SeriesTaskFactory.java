package ru.shift.task;

import lombok.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.LongToDoubleFunction;

public final class SeriesTaskFactory implements TaskFactory {
    @Override
    public List<SeriesTask> createTasks(
            @NonNull LongToDoubleFunction function,
            long startInclusive,
            long endExclusive,
            int taskCount
    ) {

        if (startInclusive > endExclusive) {
            throw new IllegalArgumentException("startInclusive must be less than or equal to endExclusive");
        }

        if (taskCount <= 0) {
            throw new IllegalArgumentException("taskCount must be positive");
        }

        long numbersCount = endExclusive - startInclusive;
        if (numbersCount == 0) {
            return Collections.emptyList();
        }

        int actualTaskCount = (int) Math.min(numbersCount, taskCount);
        long chunkSize = numbersCount / actualTaskCount;
        if (numbersCount % actualTaskCount != 0) {
            chunkSize++;
        }

        List<SeriesTask> tasks = new ArrayList<>(actualTaskCount);
        long nextStart = startInclusive;
        long remainingNumbers = numbersCount;

        int i = 0;
        while (remainingNumbers > 0) {
            long currentChunkSize = Math.min(chunkSize, remainingNumbers);
            long end = nextStart + currentChunkSize;

            tasks.add(new SeriesTask(i, function, nextStart, end));

            nextStart = end;
            remainingNumbers -= currentChunkSize;
            i++;
        }

        return tasks;
    }
}
