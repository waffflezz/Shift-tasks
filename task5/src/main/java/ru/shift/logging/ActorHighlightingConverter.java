package ru.shift.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * Выделяет цветом сообщения логов в зависимости от типа рабочего потока.
 */
public class ActorHighlightingConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {
    /**
     * Возвращает ANSI-код цвета для события логирования.
     *
     * @param event событие логирования
     * @return код цвета для потока производителя, потребителя или значение по умолчанию
     */
    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        String threadName = event.getThreadName();

        if (threadName.startsWith("producer-")) {
            return ANSIConstants.BLUE_FG;
        }

        if (threadName.startsWith("consumer-")) {
            return ANSIConstants.GREEN_FG;
        }

        return ANSIConstants.DEFAULT_FG;
    }
}
