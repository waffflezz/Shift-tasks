package ru.shift.task;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.LongToDoubleFunction;

@Slf4j
public final class SeriesTask implements Callable<Double>, TaskIdentifier {
    private final int id;
    private final LongToDoubleFunction function;
    private final long startInclusive;
    private final long endExclusive;
    @Getter
    private double result;

    public SeriesTask(int id, LongToDoubleFunction function, long startInclusive, long endExclusive) {
        this.id = id;
        this.function = function;

        if (startInclusive > endExclusive) {
            throw new IllegalArgumentException("startInclusive must be less than or equal to endExclusive");
        }

        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Double call() {
        result = calculateSum();
        log.info(
                "SeriesTask id={} completed range=[{}, {}), result={}",
                getId(),
                startInclusive,
                endExclusive,
                result
        );
        return result;
    }

    private double calculateSum() {
        double sum = 0.0;

        for (long n = startInclusive; n < endExclusive; n++) {
            sum += function.applyAsDouble(n);
        }

        return sum;
    }
}
