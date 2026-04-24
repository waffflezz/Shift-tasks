package ru.shift.solver;

import ru.shift.series.Series;

/**
 * Контракт для вычислителя конечной суммы числового ряда.
 */
public interface SeriesSolver {
    /**
     * Возвращает краткое имя реализации солвера.
     *
     * @return имя солвера
     */
    String name();

    /**
     * Вычисляет сумму первых {@code termsCount} членов ряда.
     *
     * @param series описание ряда
     * @param termsCount количество членов для суммирования
     * @return вычисленная сумма
     */
    double calculate(Series series, long termsCount);
}
