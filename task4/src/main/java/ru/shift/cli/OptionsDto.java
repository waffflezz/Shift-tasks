package ru.shift.cli;

import ru.shift.solver.FixedExecutorSeriesSolver;
import ru.shift.solver.ForkJoinSeriesSolver;

/**
 * Набор параметров командной строки, влияющих на выбор и настройку солверов.
 *
 * @param threshold порог значения {@code N}, при котором выбирается многопоточный режим
 * @param withTimeMeasurement Нужно ли замерять время
 * @param fixedThreads размер пула потоков для {@link FixedExecutorSeriesSolver}
 * @param fixedTasks количество задач для {@link FixedExecutorSeriesSolver}, на частей будет дробиться ряд
 * @param forkThreads уровень параллелизма для {@link ForkJoinSeriesSolver}
 * @param forkThreshold порог разбиения задач для {@link ForkJoinSeriesSolver}
 */
public record OptionsDto(
        long threshold,
        boolean withTimeMeasurement,
        Integer fixedThreads,
        Integer fixedTasks,
        Integer forkThreads,
        Long forkThreshold
) {}
