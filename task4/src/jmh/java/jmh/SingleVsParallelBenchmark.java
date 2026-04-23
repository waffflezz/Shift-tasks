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
import ru.shift.solver.FixedExecutorSeriesSolver;
import ru.shift.solver.ForkJoinSeriesSolver;
import ru.shift.solver.SeriesSolver;
import ru.shift.solver.SingleThreadSeriesSolver;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
public class SingleVsParallelBenchmark {
    private final static int FIXED_THREADS = 8;
    private final static int FIXED_TASKS = 100;

    private final static int FORK_THREADS = 8;
    private final static long FORK_THRESHOLD = 1000;

    @Param({"powers-of-two"})
    public String seriesCode;

    @Param({"100", "1000", "10000", "100000", "1000000", "10000000"})
    public long termsCount;

    private Series series;
    private SeriesSolver singleThreadSolver;
    private SeriesSolver fixedExecutorSolver;
    private SeriesSolver forkJoinSolver;

    @Setup
    public void setUp() {
        series = BenchmarkSeries.find(seriesCode);
        singleThreadSolver = new SingleThreadSeriesSolver();
        fixedExecutorSolver = new FixedExecutorSeriesSolver(FIXED_THREADS, FIXED_TASKS);
        forkJoinSolver = new ForkJoinSeriesSolver(FORK_THREADS, FORK_THRESHOLD);
    }

    @Benchmark
    public double singleThread() {
        return singleThreadSolver.calculate(series, termsCount);
    }

    @Benchmark
    public double fixedExecutorDefault() {
        return fixedExecutorSolver.calculate(series, termsCount);
    }

    @Benchmark
    public double forkJoinDefault() {
        return forkJoinSolver.calculate(series, termsCount);
    }
}
