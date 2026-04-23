package ru.shift.series;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class SeriesRegistry implements Registry<Series, String> {
    private static final double GEOMETRIC_Q = 1.0 / 3.0;

    private final Map<String, Series> seriesByCode = createSeriesByCode();

    @Override
    public Collection<Series> getAll() {
        return seriesByCode.values();
    }

    @Override
    public Optional<Series> find(String code) {
        if (code == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(seriesByCode.get(normalizeCode(code)));
    }

    @Override
    public void register(Series series) {
        register(seriesByCode, series);
    }

    @Override
    public Optional<Series> remove(String code) {
        if (code == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(seriesByCode.remove(normalizeCode(code)));
    }

    private static Map<String, Series> createSeriesByCode() {
        Map<String, Series> seriesByCode = new LinkedHashMap<>();

        register(seriesByCode, new Series(
                "powers-of-two",
                "Powers of two",
                "1 / 2^i",
                0,
                n -> 1.0 / Math.pow(2.0, n),
                2.0
        ));
        register(seriesByCode, new Series(
                "inverse-squares",
                "Inverse squares",
                "1 / n^2",
                1,
                n -> 1.0 / (n * (double) n),
                Math.PI * Math.PI / 6.0
        ));
        register(seriesByCode, new Series(
                "telescopic",
                "Telescopic series",
                "1 / (n * (n + 1))",
                1,
                n -> 1.0 / (n * (n + 1.0)),
                1.0
        ));
        register(seriesByCode, new Series(
                "geometric-q",
                "Geometric series",
                "q^n, q = 1 / 3",
                0,
                n -> Math.pow(GEOMETRIC_Q, n),
                1.0 / (1.0 - GEOMETRIC_Q)
        ));

        return seriesByCode;
    }

    private static void register(Map<String, Series> seriesByCode, Series series) {
        seriesByCode.put(normalizeCode(series.code()), series);
    }

    private static String normalizeCode(String code) {
        return code.trim().toLowerCase();
    }
}
