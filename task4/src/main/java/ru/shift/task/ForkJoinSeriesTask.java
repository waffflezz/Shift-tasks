package ru.shift.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongToDoubleFunction;

/**
 * Рекурсивная задача для вычисления части суммы ряда в модели {@code fork-join}.
 */
@Slf4j
public final class ForkJoinSeriesTask extends RecursiveTask<Double> implements TaskIdentifier {
    private final int id;
    private final LongToDoubleFunction function;
    private final long startInclusive;
    private final long endExclusive;
    private final long threshold;
    private final AtomicInteger idSequence;

    /**
     * Создаёт корневую задачу для вычисления суммы на заданном диапазоне индексов.
     *
     * @param id идентификатор корневой задачи
     * @param function функция, вычисляющая член ряда по индексу
     * @param startInclusive начальный индекс диапазона включительно
     * @param endExclusive конечный индекс диапазона исключительно
     * @param threshold максимальный размер диапазона без дальнейшего разбиения
     */
    public ForkJoinSeriesTask(
            int id,
            LongToDoubleFunction function,
            long startInclusive,
            long endExclusive,
            long threshold
    ) {
        if (startInclusive > endExclusive) {
            throw new IllegalArgumentException("startInclusive must be less than or equal to endExclusive");
        }

        if (threshold <= 0) {
            throw new IllegalArgumentException("threshold must be positive");
        }

        this.id = id;
        this.function = function;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.threshold = threshold;
        this.idSequence = new AtomicInteger(id);
    }

    /**
     * Создаёт дочернюю подзадачу на основе родительской задачи для поддиапазона индексов.
     *
     * @param parentTask родительская задача
     * @param startInclusive начальный индекс поддиапазона включительно
     * @param endExclusive конечный индекс поддиапазона исключительно
     */
    private ForkJoinSeriesTask(
            ForkJoinSeriesTask parentTask,
            long startInclusive,
            long endExclusive
    ) {
        if (startInclusive > endExclusive) {
            throw new IllegalArgumentException("startInclusive must be less than or equal to endExclusive");
        }
        this.idSequence = parentTask.idSequence;
        this.id = this.idSequence.incrementAndGet();
        this.function = parentTask.function;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.threshold = parentTask.threshold;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    protected Double compute() {
        long numbersCount = endExclusive - startInclusive;

        if (numbersCount <= threshold) {
            double result = calculateSum();
            log.info(
                    "ForkJoinSeriesTask id={} completed range=[{}, {}), result={}",
                    getId(),
                    startInclusive,
                    endExclusive,
                    result
            );
            return result;
        }

        return getResult(numbersCount);
    }

    /**
     * Делит текущий диапазон на две подзадачи и объединяет их результаты.
     *
     * @param numbersCount размер обрабатываемого диапазона
     * @return сумма результатов левой и правой подзадач
     */
    private double getResult(long numbersCount) {
        long middle = startInclusive + numbersCount / 2;
        ForkJoinSeriesTask leftTask = new ForkJoinSeriesTask(
                this,
                startInclusive,
                middle
        );
        ForkJoinSeriesTask rightTask = new ForkJoinSeriesTask(
                this,
                middle,
                endExclusive
        );

        leftTask.fork();
        double rightSum = rightTask.compute();
        double leftSum = leftTask.join();
        return leftSum + rightSum;
    }

    /**
     * Последовательно вычисляет сумму ряда на диапазоне текущей задачи.
     *
     * @return частичная сумма ряда
     */
    private double calculateSum() {
        double sum = 0.0;

        for (long n = startInclusive; n < endExclusive; n++) {
            sum += function.applyAsDouble(n);
        }

        return sum;
    }
}
