package jmh;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.shift.series.Series;
import ru.shift.series.SeriesRegistry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class BenchmarkSeries {
    static Series find(String code) {
        return new SeriesRegistry().find(code)
                .orElseThrow(() -> new IllegalArgumentException("Unknown series: " + code));
    }

    static String formula(String code) {
        return find(code).formula();
    }
}
