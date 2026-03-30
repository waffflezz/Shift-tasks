package ru.shift.factories;

import lombok.extern.slf4j.Slf4j;
import ru.shift.shapes.Circle;
import ru.shift.shapes.types.ShapeType;
import ru.shift.utils.ParserUtil;

@Slf4j
public class CircleFactory implements ShapeFactory<Circle> {
    private final static int PARAMS_NEED = 1;

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CIRCLE;
    }

    @Override
    public Circle create(String[] params) {
        log.info("Создание фигуры: {}", getShapeType());
        try {
            FactoriesValidator.validateParamsCount(params, PARAMS_NEED, getShapeType());
            return new Circle(ParserUtil.parsePositiveDouble(params[0]));
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
