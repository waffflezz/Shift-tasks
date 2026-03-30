package ru.shift.factories;

import lombok.extern.slf4j.Slf4j;
import ru.shift.shapes.Triangle;
import ru.shift.shapes.types.ShapeType;
import ru.shift.utils.ParserUtil;

import java.util.Arrays;

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
public class TriangleFactory implements ShapeFactory<Triangle> {
    private final static int PARAMS_NEED = 3;

    @Override
    public ShapeType getShapeType() {
        return ShapeType.TRIANGLE;
    }

    @Override
    public Triangle create(String[] params) {
        log.info("Создание фигуры: {}", getShapeType());
        try {
            FactoriesValidator.validateParamsCount(params, PARAMS_NEED, getShapeType());
            var parsedParams = Arrays.stream(params).mapToDouble(ParserUtil::parsePositiveDouble).toArray();
            return new Triangle(parsedParams[0], parsedParams[1], parsedParams[2]);
        } catch (Exception e) {
            log.error("При создании фигуры: {}, произошла ошибка: {}", getShapeType(), e.getMessage());
            log.debug("Подробности ошибки: ", e);
            throw e;
        }
    }

    @Override
    public int getParamsNeedCount() {
        return PARAMS_NEED;
    }
}
