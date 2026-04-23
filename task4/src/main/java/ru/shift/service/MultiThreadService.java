package ru.shift.service;

import ru.shift.cli.OptionsDto;
import ru.shift.constants.Messages;
import ru.shift.io.ConsoleInput;
import ru.shift.series.Series;
import ru.shift.series.SeriesRegistry;
import ru.shift.solver.FixedExecutorSeriesSolver;
import ru.shift.solver.ForkJoinSeriesSolver;
import ru.shift.solver.SeriesSolver;
import ru.shift.solver.SingleThreadSeriesSolver;
import ru.shift.solver.TimedSeriesSolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public final class MultiThreadService {
    public void run(OptionsDto options) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        SeriesRegistry seriesRegistry = new SeriesRegistry();
        Series series = readSeries(scanner, seriesRegistry);
        SeriesSolver multithreadSolver = readMultithreadSolver(scanner, options);
        long termsCount = readTermsCount(scanner);
        SeriesSolver solver = selectSolver(termsCount, multithreadSolver, options.threshold());

        if (options.withTimeMeasurement()) {
            solver = new TimedSeriesSolver(solver);
        }

        printCalculation(series, solver, termsCount);
    }

    private static Series readSeries(Scanner scanner, SeriesRegistry seriesRegistry) {
        List<Series> series = List.copyOf(seriesRegistry.getAll());

        if (series.isEmpty()) {
            throw new IllegalStateException("Series registry is empty");
        }

        System.out.println(Messages.AVAILABLE_FUNCTIONS);
        for (int index = 0; index < series.size(); index++) {
            System.out.printf(Messages.MENU_ITEM, index + 1, series.get(index).formula());
        }

        while (true) {
            System.out.print(Messages.CHOOSE_FUNCTION);
            int selectedIndex = ConsoleInput.readInt(scanner);

            if (selectedIndex >= 1 && selectedIndex <= series.size()) {
                return series.get(selectedIndex - 1);
            }

            System.out.printf(Messages.FUNCTION_NUMBER_MUST_BE_BETWEEN, series.size());
        }
    }

    private static SeriesSolver readMultithreadSolver(Scanner scanner, OptionsDto options) {
        List<SolverOption> solverOptions = List.of(
                new SolverOption(
                        Messages.FIXED_EXECUTOR,
                        new FixedExecutorSeriesSolver(
                                options.fixedThreads(),
                                options.fixedTasks() == null ? options.fixedThreads() : options.fixedTasks()
                        )
                ),
                new SolverOption(
                        Messages.FORK_JOIN,
                        new ForkJoinSeriesSolver(options.forkThreads(), options.forkThreshold())
                )
        );

        System.out.println(Messages.AVAILABLE_MULTITHREAD_SOLVERS);
        for (int index = 0; index < solverOptions.size(); index++) {
            System.out.printf(Messages.MENU_ITEM, index + 1, solverOptions.get(index).name());
        }

        while (true) {
            System.out.print(Messages.CHOOSE_MULTITHREAD_SOLVER);
            int selectedIndex = ConsoleInput.readInt(scanner);

            if (selectedIndex >= 1 && selectedIndex <= solverOptions.size()) {
                return solverOptions.get(selectedIndex - 1).solver();
            }

            System.out.printf(Messages.SOLVER_NUMBER_MUST_BE_BETWEEN, solverOptions.size());
        }
    }

    private static long readTermsCount(Scanner scanner) {
        System.out.print(Messages.ENTER_N);
        long termsCount = ConsoleInput.readLong(scanner);

        if (termsCount < 0) {
            throw new IllegalArgumentException("N must be non-negative");
        }

        return termsCount;
    }

    private static SeriesSolver selectSolver(long termsCount, SeriesSolver multithreadSolver, long threshold) {
        if (termsCount <= threshold) {
            System.out.printf(Messages.USING_SINGLE_THREAD_SOLVER, termsCount, threshold);
            return new SingleThreadSeriesSolver();
        }

        System.out.printf(Messages.USING_MULTITHREAD_SOLVER, termsCount, threshold, multithreadSolver.name());
        return multithreadSolver;
    }

    private static void printCalculation(
            Series series,
            SeriesSolver solver,
            long termsCount
    ) {
        double result = solver.calculate(series, termsCount);

        System.out.printf(Messages.CALCULATION_RESULT,
                solver.name(),
                termsCount,
                series.formula(),
                result,
                series.expectedInfiniteSum()
        );
    }

    private record SolverOption(String name, SeriesSolver solver) {
    }
}
