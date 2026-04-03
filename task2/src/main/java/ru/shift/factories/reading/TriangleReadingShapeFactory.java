package ru.shift.factories.reading;

import lombok.extern.slf4j.Slf4j;
import ru.shift.io.InputReader;
import ru.shift.shapes.Triangle;
import ru.shift.utils.ParserUtil;

import java.io.IOException;

/**
 * Фабрика для создания объектов {@link Triangle}.
 *
 * <p>Преобразует входные строковые параметры в числовые значения
 * и создаёт треугольник после валидации.</p>
 *
 * <p>Ожидается, что входной массив содержит ровно {@link #PARAMS_NEED} параметра:
 * длины сторон треугольника.</p>
 */
@Slf4j
public class TriangleReadingShapeFactory extends ReadingShapeFactory<Triangle> {
    private final static int PARAMS_NEED = 3;
    private final static int PARAMS_LINE_MAX_LEN = PARAMS_NEED * String.valueOf(Double.MAX_VALUE).length();

    @Override
    public String getShapeType() {
        return "TRIANGLE";
    }

    @Override
    public Triangle create(InputReader reader) throws IOException {
        log.info("Создание фигуры: {}", getShapeType());
        var params = readParams(reader, PARAMS_LINE_MAX_LEN);

        return new Triangle(
                ParserUtil.parsePositiveDouble(params[0]),
                ParserUtil.parsePositiveDouble(params[1]),
                ParserUtil.parsePositiveDouble(params[2])
        );
    }

    @Override
    public int getParamsNeedCount() {
        return PARAMS_NEED;
    }
}