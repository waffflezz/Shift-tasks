package ru.shift.factories.reading;

import lombok.extern.slf4j.Slf4j;
import ru.shift.factories.FactoriesValidator;
import ru.shift.io.InputReader;
import ru.shift.shapes.Circle;
import ru.shift.utils.ParserUtil;

import java.io.IOException;

/**
 * Фабрика для создания объектов {@link Circle}.
 *
 * <p>Преобразует входной строковый параметр в числовое значение
 * и создаёт окружность после валидации.</p>
 *
 * <p>Ожидается, что входной массив содержит ровно 1 параметр:
 * радиус окружности.</p>
 */
@Slf4j
public class CircleReadingShapeFactory implements ReadingShapeFactory<Circle> {
    private final static int PARAMS_NEED = 1;
    private final static int PARAMS_LINE_MAX_LEN = PARAMS_NEED * String.valueOf(Double.MAX_VALUE).length();

    @Override
    public String getShapeType() {
        return "CIRCLE";
    }

    @Override
    public Circle create(InputReader reader) throws IOException {
        String[] params = reader.readLine(PARAMS_LINE_MAX_LEN).split("\\s");
        FactoriesValidator.validateParamsCount(params, getParamsNeedCount(), getShapeType());

        return new Circle(ParserUtil.parsePositiveDouble(params[0]));
    }

    @Override
    public int getParamsNeedCount() {
        return PARAMS_NEED;
    }
}
