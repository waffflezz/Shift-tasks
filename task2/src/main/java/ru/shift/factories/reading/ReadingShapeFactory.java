package ru.shift.factories.reading;

import ru.shift.factories.FactoriesValidator;
import ru.shift.factories.ShapeFactory;
import ru.shift.io.InputReader;
import ru.shift.shapes.Shape;

import java.io.IOException;

/**
 * Абстрактная фабрика для создания фигур на основе входного потока данных.
 *
 * <p>Реализует базовую логику чтения и валидации параметров фигуры
 * из {@link InputReader}. Конкретные реализации отвечают за преобразование
 * параметров в соответствующий тип {@link Shape}.</p>
 *
 * @param <S> тип создаваемой фигуры
 */
public abstract class ReadingShapeFactory<S extends Shape> implements ShapeFactory<S, InputReader> {
    /**
     * Считывает и валидирует параметры фигуры из входного потока.
     *
     * <p>Метод выполняет:
     * <ul>
     *     <li>Чтение строки из {@link InputReader}</li>
     *     <li>Проверку на пустую строку</li>
     *     <li>Разделение строки на параметры</li>
     *     <li>Проверку количества параметров</li>
     * </ul>
     * </p>
     *
     * @param reader источник входных данных
     * @param paramsMaxLineLen максимальная длина строки с параметрами
     * @return массив параметров, полученных из строки
     * @throws IOException если произошла ошибка чтения
     * @throws IllegalArgumentException если строка пустая или количество параметров некорректно
     */
    protected String[] readParams(InputReader reader, int paramsMaxLineLen) throws IOException {
        String line = reader.readLine(paramsMaxLineLen);
        FactoriesValidator.validateBlankLine(line);

        String[] params = line.split("\\s+");
        FactoriesValidator.validateParamsCount(params, getParamsNeedCount(), getShapeType());

        return params;
    }
}
