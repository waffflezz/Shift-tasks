package ru.shift.series;

import java.util.function.LongToDoubleFunction;

/**
 * Описание числового ряда, доступного для вычисления.
 *
 * @param code уникальный код ряда
 * @param name человекочитаемое название ряда
 * @param formula текстовое представление формулы ряда
 * @param firstIndex индекс первого суммируемого элемента
 * @param seriesFunction функция вычисления члена ряда по индексу
 * @param expectedInfiniteSum ожидаемая сумма бесконечного ряда
 */
public record Series(
        String code,
        String name,
        String formula,
        long firstIndex,
        LongToDoubleFunction seriesFunction,
        double expectedInfiniteSum
) {}
