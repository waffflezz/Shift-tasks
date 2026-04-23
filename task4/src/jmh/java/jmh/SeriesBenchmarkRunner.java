package jmh;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.CommandLineOptions;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SeriesBenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options options = createOptions(args);
        Collection<RunResult> results = new Runner(options).run();

        BenchmarkReportPrinter.print(results);
    }

    private static Options createOptions(String[] args) throws Exception {
        ChainedOptionsBuilder optionsBuilder = new OptionsBuilder()
                .shouldFailOnError(true);

        if (args.length == 0) {
            return optionsBuilder
                    .include("jmh.SingleVsParallelBenchmark")
                    .include("jmh.FixedExecutorTuningBenchmark")
                    .include("jmh.ForkJoinTuningBenchmark")
                    .build();
        }

        return optionsBuilder
                .parent(new CommandLineOptions(args))
                .build();
    }
}
