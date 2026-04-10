package ru.shift.factories.reading;

import lombok.extern.slf4j.Slf4j;
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
public class CircleReadingShapeFactory extends ReadingShapeFactory<Circle> {
    private final static int PARAMS_NEED = 1;

    @Override
    public String getShapeType() {
        return "CIRCLE";
    }

    @Override
    public Circle create(InputReader reader) throws IOException {
        log.info("Создание фигуры: {}", getShapeType());
        var line = reader.readLine();
        return new Circle(ParserUtil.parsePositiveDouble(line));
    }

    @Override
    public int getParamsNeedCount() {
        return PARAMS_NEED;
    }
}
