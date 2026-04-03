package ru.shift.factories.reading;

import lombok.extern.slf4j.Slf4j;
import ru.shift.io.InputReader;
import ru.shift.shapes.Rectangle;
import ru.shift.utils.ParserUtil;

import java.io.IOException;

/**
 * Фабрика для создания объектов {@link Rectangle}.
 *
 * <p>Преобразует входные строковые параметры в числовые значения
 * и создаёт прямоугольник после валидации.</p>
 *
 * <p>Ожидается, что входной массив содержит ровно {@link #PARAMS_NEED} параметра:
 * ширину и высоту прямоугольника.</p>
 */
@Slf4j
public class RectangleReadingShapeFactory extends ReadingShapeFactory<Rectangle> {
    private final static int PARAMS_NEED = 2;
    private final static int PARAMS_LINE_MAX_LEN = PARAMS_NEED * String.valueOf(Double.MAX_VALUE).length();

    @Override
    public String getShapeType() {
        return "RECTANGLE";
    }

    @Override
    public Rectangle create(InputReader reader) throws IOException {
        log.info("Создание фигуры: {}", getShapeType());
        var params = readParams(reader, PARAMS_LINE_MAX_LEN);

        return new Rectangle(
                ParserUtil.parsePositiveDouble(params[0]),
                ParserUtil.parsePositiveDouble(params[1])
        );
    }

    @Override
    public int getParamsNeedCount() {
        return PARAMS_NEED;
    }
}

