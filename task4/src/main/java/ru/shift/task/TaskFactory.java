package ru.shift.task;

import java.util.List;
import java.util.function.LongToDoubleFunction;

/**
 * Определяет способ построения набора задач для вычисления суммы ряда.
 */
public interface TaskFactory {
    /**
     * Создаёт задачи для вычисления диапазона индексов ряда.
     *
     * @param function функция вычисления члена ряда
     * @param startInclusive начальный индекс диапазона включительно
     * @param endExclusive конечный индекс диапазона исключительно
     * @param taskCount желаемое количество задач
     * @return список созданных задач
     */
    List<SeriesTask> createTasks(
            LongToDoubleFunction function,
            long startInclusive,
            long endExclusive,
            int taskCount
    );
}
