package jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import ru.shift.series.Series;
import ru.shift.solver.ForkJoinSeriesSolver;
import ru.shift.solver.SeriesSolver;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
public class ForkJoinTuningBenchmark {
    @Param({"powers-of-two"})
    public String seriesCode;

    @Param({"100000", "1000000", "10000000"})
    public long termsCount;

    @Param({"1", "2", "4", "6", "8", "10", "12", "14", "16", "32", "64", "100"})
    public int parallelism;

    @Param({"100", "1000", "10000", "100000", "1000000"})
    public long threshold;

    private Series series;
    private SeriesSolver solver;

    @Setup
    public void setUp() {
        series = BenchmarkSeries.find(seriesCode);
        solver = new ForkJoinSeriesSolver(parallelism, threshold);
    }

    @Benchmark
    public double forkJoin() {
        return solver.calculate(series, termsCount);
    }
}
