package ru.shift.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class ActorHighlightingConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {
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
