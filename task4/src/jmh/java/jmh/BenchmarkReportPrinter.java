package jmh;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openjdk.jmh.results.RunResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class BenchmarkReportPrinter {
    static void print(Collection<RunResult> results) {
        if (results.isEmpty()) {
            return;
        }

        System.out.println();
        printSingleVsParallel(results);
        printSingleVsBestParallel(results);
        printBestFixedExecutor(results);
        printBestForkJoin(results);
    }

    private static void printSingleVsParallel(Collection<RunResult> results) {
        Map<Key, Map<String, RunResult>> groupedResults = new LinkedHashMap<>();

        for (RunResult result : results) {
            String method = methodName(result);
            if (!List.of("singleThread", "fixedExecutorDefault", "forkJoinDefault").contains(method)) {
                continue;
            }

            Key key = new Key(param(result, "seriesCode"), param(result, "termsCount"));
            groupedResults.computeIfAbsent(key, ignored -> new LinkedHashMap<>()).put(method, result);
        }

        if (groupedResults.isEmpty()) {
            return;
        }

        System.out.println("Single vs Default Parallel");
        printSeparator();
        System.out.printf("%-24s %12s %12s %12s %12s %-18s\n",
                "formula", "terms", "single", "fixed", "forkJoin", "winner");
        printSeparator();

        for (Map.Entry<Key, Map<String, RunResult>> entry : groupedResults.entrySet()) {
            Map<String, RunResult> methods = entry.getValue();
            RunResult single = methods.get("singleThread");
            RunResult fixed = methods.get("fixedExecutorDefault");
            RunResult forkJoin = methods.get("forkJoinDefault");

            System.out.printf("%-24s %12s %12s %12s %12s %-18s\n",
                    formula(entry.getKey().seriesCode()),
                    entry.getKey().termsCount(),
                    score(single),
                    score(fixed),
                    score(forkJoin),
                    winner(methods));
        }

        System.out.println();
    }

    private static void printSingleVsBestParallel(Collection<RunResult> results) {
        Map<Key, RunResult> singleResults = mapBySeriesAndTerms(filterByMethod(results, "singleThread"));
        Map<Key, RunResult> bestFixedResults = mapBySeriesAndTerms(bestBySeriesAndTerms(filterByMethod(results, "fixedExecutor")));
        Map<Key, RunResult> bestForkJoinResults = mapBySeriesAndTerms(bestBySeriesAndTerms(filterByMethod(results, "forkJoin")));

        if (singleResults.isEmpty() || (bestFixedResults.isEmpty() && bestForkJoinResults.isEmpty())) {
            return;
        }

        System.out.println("Single vs Best Tuned Parallel");
        printSeparator();
        System.out.printf("%-24s %12s %12s %12s %12s %-18s\n",
                "formula", "terms", "single", "bestFixed", "bestFork", "winner");
        printSeparator();

        for (Map.Entry<Key, RunResult> entry : singleResults.entrySet()) {
            Key key = entry.getKey();
            RunResult fixed = bestFixedResults.get(key);
            RunResult forkJoin = bestForkJoinResults.get(key);

            if (fixed == null && forkJoin == null) {
                continue;
            }

            Map<String, RunResult> methods = new LinkedHashMap<>();
            methods.put("singleThread", entry.getValue());
            if (fixed != null) {
                methods.put("fixedExecutor", fixed);
            }
            if (forkJoin != null) {
                methods.put("forkJoin", forkJoin);
            }

            System.out.printf("%-24s %12s %12s %12s %12s %-18s\n",
                    formula(key.seriesCode()),
                    key.termsCount(),
                    score(entry.getValue()),
                    score(fixed),
                    score(forkJoin),
                    winner(methods));
        }

        System.out.println();
    }

    private static void printBestFixedExecutor(Collection<RunResult> results) {
        List<RunResult> fixedResults = filterByMethod(results, "fixedExecutor");
        if (fixedResults.isEmpty()) {
            return;
        }

        System.out.println("Best FixedExecutor Settings");
        printSeparator();
        System.out.printf("%-24s %12s %10s %10s %12s\n",
                "formula", "terms", "pool", "tasks", "ms/op");
        printSeparator();

        for (RunResult result : bestBySeriesAndTerms(fixedResults)) {
            System.out.printf("%-24s %12s %10s %10s %12s\n",
                    formula(param(result, "seriesCode")),
                    param(result, "termsCount"),
                    param(result, "poolSize"),
                    param(result, "taskCount"),
                    score(result));
        }

        System.out.println();
    }

    private static void printBestForkJoin(Collection<RunResult> results) {
        List<RunResult> forkJoinResults = filterByMethod(results, "forkJoin");
        if (forkJoinResults.isEmpty()) {
            return;
        }

        System.out.println("Best ForkJoin Settings");
        printSeparator();
        System.out.printf("%-24s %12s %12s %12s %12s\n",
                "formula", "terms", "parallelism", "threshold", "ms/op");
        printSeparator();

        for (RunResult result : bestBySeriesAndTerms(forkJoinResults)) {
            System.out.printf("%-24s %12s %12s %12s %12s\n",
                    formula(param(result, "seriesCode")),
                    param(result, "termsCount"),
                    param(result, "parallelism"),
                    param(result, "threshold"),
                    score(result));
        }

        System.out.println();
    }

    private static List<RunResult> bestBySeriesAndTerms(List<RunResult> results) {
        Map<Key, RunResult> bestResults = new LinkedHashMap<>();

        for (RunResult result : results) {
            Key key = new Key(param(result, "seriesCode"), param(result, "termsCount"));
            RunResult currentBest = bestResults.get(key);

            if (currentBest == null || result.getPrimaryResult().getScore() < currentBest.getPrimaryResult().getScore()) {
                bestResults.put(key, result);
            }
        }

        return new ArrayList<>(bestResults.values());
    }

    private static Map<Key, RunResult> mapBySeriesAndTerms(List<RunResult> results) {
        Map<Key, RunResult> mappedResults = new LinkedHashMap<>();

        for (RunResult result : results) {
            mappedResults.put(new Key(param(result, "seriesCode"), param(result, "termsCount")), result);
        }

        return mappedResults;
    }

    private static List<RunResult> filterByMethod(Collection<RunResult> results, String method) {
        return results.stream()
                .filter(result -> method.equals(methodName(result)))
                .sorted(Comparator
                        .comparing((RunResult result) -> param(result, "seriesCode"))
                        .thenComparing(result -> Long.parseLong(param(result, "termsCount"))))
                .toList();
    }

    private static String winner(Map<String, RunResult> methods) {
        Optional<Map.Entry<String, RunResult>> winner = methods.entrySet().stream()
                .min(Comparator.comparingDouble(entry -> entry.getValue().getPrimaryResult().getScore()));

        return winner.map(entry -> displayName(entry.getKey())).orElse("-");
    }

    private static String methodName(RunResult result) {
        String benchmark = result.getParams().getBenchmark();
        return benchmark.substring(benchmark.lastIndexOf('.') + 1);
    }

    private static String param(RunResult result, String name) {
        return result.getParams().getParam(name);
    }

    private static String formula(String seriesCode) {
        return BenchmarkSeries.formula(seriesCode);
    }

    private static String score(RunResult result) {
        if (result == null) {
            return "-";
        }

        return "%.3f".formatted(result.getPrimaryResult().getScore());
    }

    private static String displayName(String method) {
        return switch (method) {
            case "singleThread" -> "single";
            case "fixedExecutorDefault", "fixedExecutor" -> "fixed";
            case "forkJoinDefault", "forkJoin" -> "forkJoin";
            default -> method;
        };
    }

    private static void printSeparator() {
        System.out.println("-".repeat(84));
    }

    private record Key(String seriesCode, String termsCount) {
    }
}
