package ru.shift.cli;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.shift.service.MultiThreadService;

@Slf4j
@Command(
        name = "task4",
        mixinStandardHelpOptions = true,
        version = "task4 1.0",
        description = "Calculates selected series using single-thread or multithread solver depending on N threshold."
)
public final class MultiThreadCommand implements Runnable {
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final long DEFAULT_FORK_THRESHOLD = 100_000;
    private final MultiThreadService multiThreadService = new MultiThreadService();

    @Option(
            names = {"-t", "--threshold"},
            required = true,
            description = "Maximum N value calculated by single-thread solver. If N is greater, selected multithread solver is used."
    )
    private long threshold;

    @Option(
            names = {"--time"},
            description = "Log calculation time with TimedSeriesSolver."
    )
    private boolean withTimeMeasurement;

    @Option(
            names = {"--fixed-threads"},
            description = "Thread pool size for fixed executor. Default: available processors."
    )
    private Integer fixedThreads = AVAILABLE_PROCESSORS;

    @Option(
            names = {"--fixed-tasks"},
            description = "Task count for fixed executor. Default: same as fixed threads."
    )
    private Integer fixedTasks;

    @Option(
            names = {"--fork-threads"},
            description = "ForkJoin parallelism. Default: available processors."
    )
    private Integer forkThreads = AVAILABLE_PROCESSORS;

    @Option(
            names = {"--fork-threshold"},
            description = "ForkJoin task splitting threshold. Default: 100000."
    )
    private Long forkThreshold = DEFAULT_FORK_THRESHOLD;

    @Override
    public void run() {
        var optionsDto = new OptionsDto(
                threshold,
                withTimeMeasurement,
                fixedThreads,
                fixedTasks,
                forkThreads,
                forkThreshold
        );

        try {
            log.info("Starting command with options={}", optionsDto);
            Validator.validateOptions(optionsDto);
            multiThreadService.run(optionsDto);
            log.info("Command completed successfully");
        } catch (IllegalArgumentException e) {
            log.error("Error with arguments or validations: {}", e.getMessage(), e);
            System.exit(2);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            System.exit(3);
        }
    }
}
