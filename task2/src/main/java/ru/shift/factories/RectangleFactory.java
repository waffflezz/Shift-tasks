package ru.shift.factories;

import lombok.extern.slf4j.Slf4j;
import ru.shift.shapes.Rectangle;
import ru.shift.shapes.types.ShapeType;
import ru.shift.utils.ParserUtil;

import java.util.Arrays;

@Slf4j
public class RectangleFactory implements ShapeFactory<Rectangle> {
    private static final int PARAMS_NEED = 2;

    @Override
    public ShapeType getShapeType() {
        return ShapeType.RECTANGLE;
    }

    @Override
    public Rectangle create(String[] params) {
        log.info("Создание фигуры: {}", getShapeType());
        try {
            FactoriesValidator.validateParamsCount(params, PARAMS_NEED, getShapeType());
            var parsedParams = Arrays.stream(params).mapToDouble(ParserUtil::parsePositiveDouble).toArray();
            return new Rectangle(parsedParams[0], parsedParams[1]);
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
